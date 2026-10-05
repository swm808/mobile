package com.citybond.mobile.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.citybond.mobile.network.AssistantConversationMessageRequest
import com.citybond.mobile.network.AssistantConversationResponse
import com.citybond.mobile.network.AssistantConversationSummary
import com.citybond.mobile.network.AssistantMessageResponse
import com.citybond.mobile.network.CityBondServices
import com.citybond.mobile.network.LoginRequest
import com.citybond.mobile.network.NetworkClient
import java.io.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

data class AssistantUiState(
    val checkingSession: Boolean = true,
    val authenticated: Boolean = false,
    val canUseAssistant: Boolean = false,
    val accountName: String = "",
    val username: String = "",
    val password: String = "",
    val captchaToken: String? = null,
    val captchaImage: String? = null,
    val captchaText: String = "",
    val loggingIn: Boolean = false,
    val conversations: List<AssistantConversationSummary> = emptyList(),
    val currentConversation: AssistantConversationResponse? = null,
    val input: String = "",
    val loadingConversation: Boolean = false,
    val sending: Boolean = false,
    val errorMessage: String? = null,
)

class AssistantViewModel(
    private val services: CityBondServices = NetworkClient.createCityBond(),
) : ViewModel() {
    private val mutableState = MutableStateFlow(AssistantUiState())
    val state = mutableState.asStateFlow()

    init {
        refreshSession()
    }

    fun changeUsername(value: String) = mutableState.update { it.copy(username = value, errorMessage = null) }
    fun changePassword(value: String) = mutableState.update { it.copy(password = value, errorMessage = null) }
    fun changeCaptcha(value: String) = mutableState.update { it.copy(captchaText = value, errorMessage = null) }
    fun changeInput(value: String) = mutableState.update { it.copy(input = value.take(4000), errorMessage = null) }
    fun dismissError() = mutableState.update { it.copy(errorMessage = null) }

    fun refreshSession() {
        viewModelScope.launch {
            mutableState.update { it.copy(checkingSession = true, errorMessage = null) }
            try {
                val user = services.auth.me()
                val canUseAssistant = "assistant.view" in user.permissions || user.roleCode == "super_admin"
                mutableState.update {
                    it.copy(
                        checkingSession = false,
                        authenticated = true,
                        canUseAssistant = canUseAssistant,
                        accountName = user.name.ifBlank { user.username },
                    )
                }
                if (canUseAssistant) loadConversationList()
            } catch (error: CancellationException) {
                throw error
            } catch (error: HttpException) {
                if (error.code() == 401) showLoggedOut() else showError(error)
            } catch (_: IOException) {
                mutableState.update {
                    it.copy(checkingSession = false, errorMessage = "无法连接知城智融服务")
                }
            }
        }
    }

    fun login() {
        val snapshot = state.value
        if (snapshot.username.isBlank() || snapshot.password.isBlank() || snapshot.loggingIn) return
        viewModelScope.launch {
            mutableState.update { it.copy(loggingIn = true, errorMessage = null) }
            try {
                val user = services.auth.login(
                    LoginRequest(
                        username = snapshot.username.trim(),
                        password = snapshot.password,
                        captchaToken = snapshot.captchaToken,
                        captchaText = snapshot.captchaText.ifBlank { null },
                    ),
                )
                val canUseAssistant = "assistant.view" in user.permissions || user.roleCode == "super_admin"
                mutableState.update {
                    it.copy(
                        checkingSession = false,
                        authenticated = true,
                        canUseAssistant = canUseAssistant,
                        loggingIn = false,
                        password = "",
                        captchaToken = null,
                        captchaImage = null,
                        captchaText = "",
                        accountName = user.name.ifBlank { user.username },
                    )
                }
                if (canUseAssistant) loadConversationList()
            } catch (error: CancellationException) {
                throw error
            } catch (error: HttpException) {
                mutableState.update { it.copy(loggingIn = false, errorMessage = apiErrorMessage(error)) }
                if (error.code() == 428 || error.code() == 401) loadCaptcha()
            } catch (_: IOException) {
                mutableState.update { it.copy(loggingIn = false, errorMessage = "登录失败，请检查服务连接") }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            runCatching { services.auth.logout() }
            NetworkClient.clearSession()
            showLoggedOut()
        }
    }

    fun newConversation() {
        if (!state.value.authenticated || !state.value.canUseAssistant || state.value.loadingConversation) return
        viewModelScope.launch {
            mutableState.update { it.copy(loadingConversation = true, errorMessage = null) }
            try {
                val conversation = services.assistant.createConversation()
                mutableState.update { it.copy(loadingConversation = false, currentConversation = conversation) }
            } catch (error: HttpException) {
                handleAssistantError(error)
            } catch (_: IOException) {
                mutableState.update { it.copy(loadingConversation = false, errorMessage = "新建会话失败，请检查网络") }
            }
        }
    }

    fun openConversation(id: String) {
        if (state.value.loadingConversation || state.value.sending) return
        viewModelScope.launch {
            mutableState.update { it.copy(loadingConversation = true, errorMessage = null) }
            try {
                val conversation = services.assistant.conversation(id)
                mutableState.update { it.copy(loadingConversation = false, currentConversation = conversation) }
            } catch (error: HttpException) {
                handleAssistantError(error)
            } catch (_: IOException) {
                mutableState.update { it.copy(loadingConversation = false, errorMessage = "会话加载失败") }
            }
        }
    }

    fun sendMessage() {
        val content = state.value.input.trim()
        if (content.isEmpty() || state.value.sending || !state.value.authenticated || !state.value.canUseAssistant) return
        viewModelScope.launch {
            try {
                val conversation = state.value.currentConversation ?: services.assistant.createConversation()
                val optimistic = AssistantMessageResponse(
                    id = -System.currentTimeMillis(),
                    role = "user",
                    content = content,
                    createdAt = "",
                )
                mutableState.update {
                    it.copy(
                        currentConversation = conversation.copy(messages = conversation.messages + optimistic),
                        input = "",
                        sending = true,
                        errorMessage = null,
                    )
                }
                val response = services.assistant.sendMessage(
                    conversation.conversationId,
                    AssistantConversationMessageRequest(content, conversation.versionNo),
                )
                mutableState.update { it.copy(currentConversation = response, sending = false) }
                loadConversationList(selectLatest = false)
            } catch (error: CancellationException) {
                throw error
            } catch (error: HttpException) {
                handleAssistantError(error, sending = true)
            } catch (_: IOException) {
                mutableState.update {
                    it.copy(sending = false, input = content, errorMessage = "消息发送失败，请检查网络后重试")
                }
            }
        }
    }

    private fun loadCaptcha() {
        viewModelScope.launch {
            runCatching { services.auth.captcha() }
                .onSuccess { challenge ->
                    mutableState.update {
                        it.copy(
                            captchaToken = challenge.token,
                            captchaImage = challenge.image,
                            captchaText = "",
                        )
                    }
                }
        }
    }

    private suspend fun loadConversationList(selectLatest: Boolean = true) {
        try {
            val summaries = services.assistant.conversations()
            mutableState.update { it.copy(conversations = summaries) }
            if (selectLatest && state.value.currentConversation == null && summaries.isNotEmpty()) {
                val latest = services.assistant.conversation(summaries.first().conversationId)
                mutableState.update { it.copy(currentConversation = latest) }
            }
        } catch (error: HttpException) {
            handleAssistantError(error)
        } catch (_: IOException) {
            mutableState.update { it.copy(errorMessage = "历史会话加载失败") }
        }
    }

    private fun handleAssistantError(error: HttpException, sending: Boolean = false) {
        if (error.code() == 401) {
            showLoggedOut("登录已失效，请重新登录")
            return
        }
        mutableState.update {
            it.copy(
                loadingConversation = false,
                sending = false,
                errorMessage = if (error.code() == 409 && sending) {
                    "会话已在其他位置更新，请重新打开后发送"
                } else {
                    apiErrorMessage(error)
                },
            )
        }
    }

    private fun showError(error: HttpException) {
        mutableState.update { it.copy(checkingSession = false, errorMessage = apiErrorMessage(error)) }
    }

    private fun showLoggedOut(message: String? = null) {
        mutableState.update {
            AssistantUiState(
                username = it.username,
                checkingSession = false,
                errorMessage = message,
            )
        }
    }
}

@Composable
internal fun AssistantScreen(model: AssistantViewModel) {
    AssistantExperience(
        model = model,
        compact = false,
        modifier = Modifier.testTag("assistant_screen"),
    )
}

@Composable
internal fun AssistantQuickPanel(model: AssistantViewModel, openFullAssistant: () -> Unit) {
    AssistantExperience(
        model = model,
        compact = true,
        openFullAssistant = openFullAssistant,
        modifier = Modifier
            .heightIn(min = 420.dp, max = 620.dp)
            .testTag("assistant_quick_panel"),
    )
}

@Composable
private fun AssistantExperience(
    model: AssistantViewModel,
    compact: Boolean,
    modifier: Modifier = Modifier,
    openFullAssistant: (() -> Unit)? = null,
) {
    val state by model.state.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .imePadding()
            .padding(horizontal = if (compact) 16.dp else 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        AssistantHeader(state, compact, model::newConversation, openFullAssistant)
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(12.dp),
        ) {
            Text(
                "对话及上下文将保存到知城智融服务器，并遵循服务器日志与保留策略。",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 9.dp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
        if (state.canUseAssistant) {
            AssistantChat(state, model, compact)
        } else {
            AssistantPermissionRequired()
        }
    }
}

@Composable
private fun AssistantHeader(
    state: AssistantUiState,
    compact: Boolean,
    newConversation: () -> Unit,
    openFullAssistant: (() -> Unit)?,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Surface(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(14.dp)) {
            Icon(
                Icons.Outlined.Create,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(11.dp).size(22.dp),
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text("AI 智能助手", style = MaterialTheme.typography.titleLarge)
            Text(
                "${state.accountName} · 服务端会话",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        if (state.canUseAssistant && !compact) {
            OutlinedButton(
                onClick = newConversation,
                enabled = !state.loadingConversation && !state.sending,
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                modifier = Modifier.testTag("assistant_new_conversation"),
            ) {
                Icon(Icons.Outlined.Add, contentDescription = null, modifier = Modifier.size(17.dp))
                Text("新对话")
            }
        } else if (compact && openFullAssistant != null) {
            TextButton(onClick = openFullAssistant) { Text("完整页面") }
        }
    }
}

@Composable
private fun ColumnScope.AssistantPermissionRequired() {
    Box(
        modifier = Modifier.fillMaxWidth().weight(1f).testTag("assistant_permission_required"),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text("暂时无法使用助手", style = MaterialTheme.typography.titleMedium)
                Text(
                    "当前账号缺少 assistant.view 权限，请联系管理员开通。",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.AssistantChat(state: AssistantUiState, model: AssistantViewModel, compact: Boolean) {
    if (!compact && state.conversations.isNotEmpty()) {
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            state.conversations.forEach { summary ->
                AssistChip(
                    onClick = { model.openConversation(summary.conversationId) },
                    label = { Text(summary.firstUserMessage.take(18)) },
                )
            }
        }
    }
    AssistantError(state.errorMessage, model::dismissError)
    val messages = state.currentConversation?.messages.orEmpty()
    if (messages.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("你好，我是知城智融智能体", style = MaterialTheme.typography.titleMedium)
            Text(
                "可以询问融资、债务和项目管理问题。",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("解释综合成本", "梳理融资流程").forEach { suggestion ->
                    AssistChip(onClick = { model.changeInput(suggestion) }, label = { Text(suggestion) })
                }
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f).testTag("assistant_messages"),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(vertical = 4.dp),
        ) {
            items(messages, key = { it.id }) { message -> AssistantMessageBubble(message) }
            if (state.sending) {
                item {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                        Text("AI 正在思考…", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        OutlinedTextField(
            value = state.input,
            onValueChange = model::changeInput,
            placeholder = { Text("输入你想咨询的问题") },
            enabled = !state.sending,
            minLines = 1,
            maxLines = if (compact) 3 else 5,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.weight(1f).testTag("assistant_input"),
        )
        FilledTonalButton(
            onClick = model::sendMessage,
            enabled = state.input.isNotBlank() && !state.sending,
            modifier = Modifier.testTag("assistant_send"),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
        ) {
            Text("发送", fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun AssistantMessageBubble(message: AssistantMessageResponse) {
    val user = message.role == "user"
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (user) Arrangement.End else Arrangement.Start,
    ) {
        Surface(
            color = if (user) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            contentColor = if (user) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (user) 16.dp else 5.dp,
                bottomEnd = if (user) 5.dp else 16.dp,
            ),
            border = if (user) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        ) {
            Text(
                message.content,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp).fillMaxWidth(0.86f),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun AssistantError(message: String?, dismiss: () -> Unit) {
    if (message == null) return
    Surface(color = MaterialTheme.colorScheme.errorContainer, shape = RoundedCornerShape(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 12.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                message,
                modifier = Modifier.weight(1f).padding(vertical = 9.dp),
                color = MaterialTheme.colorScheme.onErrorContainer,
                style = MaterialTheme.typography.bodySmall,
            )
            TextButton(onClick = dismiss) { Text("关闭") }
        }
    }
}

private fun apiErrorMessage(error: HttpException): String {
    val fallback = when (error.code()) {
        401 -> "用户名或密码错误，请重新输入"
        403 -> "当前账号没有执行此操作的权限"
        409 -> "会话版本冲突，请重新加载"
        422 -> "输入内容不符合接口要求"
        428 -> "请输入图形验证码"
        502 -> "AI 服务暂时不可用，请稍后重试"
        else -> "服务返回 HTTP ${error.code()}"
    }
    val body = runCatching { error.response()?.errorBody()?.string() }.getOrNull() ?: return fallback
    return runCatching {
        val detail = JSONObject(body).opt("detail")
        when (detail) {
            is JSONObject -> detail.optString("message", fallback)
            is String -> detail
            else -> fallback
        }
    }.getOrDefault(fallback)
}
