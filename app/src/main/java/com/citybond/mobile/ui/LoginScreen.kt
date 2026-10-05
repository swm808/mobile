package com.citybond.mobile.ui

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.citybond.mobile.R

@Composable
internal fun AppSessionLoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("session_loading"),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(18.dp),
            ) {
                Text(
                    "知融",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 13.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
            }
            Text("知城智融智能体", style = MaterialTheme.typography.headlineSmall)
            Text(
                "正在检查登录状态…",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
            )
            CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 3.dp)
        }
    }
}

@Composable
internal fun AppLoginScreen(state: AssistantUiState, model: AssistantViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.background),
                ),
            )
            .imePadding()
            .testTag("app_login"),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.login_brand_logo),
                contentDescription = "知城智融智能体图标",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(width = 132.dp, height = 88.dp)
                    .testTag("login_brand_icon"),
            )
            Text(
                "欢迎使用知城智融智能体",
                modifier = Modifier.padding(top = 18.dp),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                "登录后进入移动工作台",
                modifier = Modifier.padding(top = 6.dp, bottom = 24.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
            )
            OutlinedCard(
                modifier = Modifier.fillMaxWidth().widthIn(max = 480.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Text("账号登录", style = MaterialTheme.typography.titleLarge)
                    Text(
                        "使用现有账号建立安全会话。",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall,
                    )
                    OutlinedTextField(
                        value = state.username,
                        onValueChange = model::changeUsername,
                        label = { Text("用户名") },
                        singleLine = true,
                        enabled = !state.loggingIn,
                        modifier = Modifier.fillMaxWidth().testTag("login_username"),
                    )
                    OutlinedTextField(
                        value = state.password,
                        onValueChange = model::changePassword,
                        label = { Text("密码") },
                        singleLine = true,
                        enabled = !state.loggingIn,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth().testTag("login_password"),
                    )
                    state.captchaImage?.let { encoded ->
                        captchaBitmap(encoded)?.let { bitmap ->
                            Image(
                                bitmap = bitmap,
                                contentDescription = "登录验证码",
                                modifier = Modifier.heightIn(max = 64.dp),
                            )
                        }
                        OutlinedTextField(
                            value = state.captchaText,
                            onValueChange = model::changeCaptcha,
                            label = { Text("图形验证码") },
                            singleLine = true,
                            enabled = !state.loggingIn,
                            modifier = Modifier.fillMaxWidth().testTag("login_captcha"),
                        )
                    }
                    LoginError(state.errorMessage, model::dismissError)
                    Button(
                        onClick = model::login,
                        enabled = !state.loggingIn && state.username.isNotBlank() && state.password.isNotBlank(),
                        modifier = Modifier.fillMaxWidth().testTag("login_submit"),
                    ) {
                        if (state.loggingIn) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp,
                            )
                        } else {
                            Text("登录并进入知城智融智能体")
                        }
                    }
                }
            }
            Text(
                "登录状态仅在当前 App 运行期间保留",
                modifier = Modifier.padding(top = 16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun LoginError(message: String?, dismiss: () -> Unit) {
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

@Composable
private fun captchaBitmap(dataUrl: String) = remember(dataUrl) {
    runCatching {
        val bytes = Base64.decode(dataUrl.substringAfter(','), Base64.DEFAULT)
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)?.asImageBitmap()
    }.getOrNull()
}
