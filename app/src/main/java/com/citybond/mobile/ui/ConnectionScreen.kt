package com.citybond.mobile.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.citybond.mobile.network.NetworkClient
import java.io.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import retrofit2.HttpException

data class ConnectionState(
    val address: String = "http://127.0.0.1:18080/",
    val checking: Boolean = false,
    val result: String? = null,
)

class ConnectionViewModel : ViewModel() {
    private val mutableState = MutableStateFlow(ConnectionState())
    val state = mutableState.asStateFlow()

    fun changeAddress(value: String) {
        if (!state.value.checking) mutableState.update { it.copy(address = value, result = null) }
    }

    fun check() {
        if (state.value.checking) return
        val address = state.value.address
        mutableState.update { it.copy(checking = true, result = null) }
        viewModelScope.launch {
            val result = try {
                val response = NetworkClient.create(address).health()
                if (response.status == "ok") "连接成功 · 服务正常" else "服务已响应，但未返回正常状态"
            } catch (error: CancellationException) {
                throw error
            } catch (error: HttpException) {
                "服务返回 HTTP ${error.code()}，请检查地址及服务状态"
            } catch (error: SerializationException) {
                "响应格式不符合健康检查协议"
            } catch (error: IllegalArgumentException) {
                "地址无效，请填写完整的 HTTP 或 HTTPS 服务根地址"
            } catch (error: IOException) {
                "连接失败，请检查服务是否启动、端口映射及网络设置"
            }
            mutableState.update { it.copy(checking = false, result = result) }
        }
    }
}

@Composable
fun ConnectionScreen(model: ConnectionViewModel = viewModel()) {
    val state by model.state.collectAsStateWithLifecycle()
    PageContent {
        Text("开发服务器", style = MaterialTheme.typography.headlineSmall)
        Text("仅请求 /health，不发送账号或业务数据。",
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        OutlinedTextField(
            value = state.address, onValueChange = model::changeAddress,
            enabled = !state.checking, singleLine = true,
            label = { Text("服务根地址") },
            modifier = Modifier.fillMaxWidth().testTag("server_address"),
        )
        Button(onClick = model::check, enabled = !state.checking,
            modifier = Modifier.testTag("check_connection")) {
            Text(if (state.checking) "检查中…" else "检查连接")
        }
        state.result?.let { Text(it, modifier = Modifier.testTag("connection_result")) }
        Text("调试版仅允许本机及官方模拟器宿主地址使用 HTTP，其他地址请使用 HTTPS。",
            style = MaterialTheme.typography.bodySmall)
    }
}
