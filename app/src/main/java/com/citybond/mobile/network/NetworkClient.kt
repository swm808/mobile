package com.citybond.mobile.network

import java.util.concurrent.TimeUnit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

@Serializable
data class HealthResponse(val status: String)

interface HealthApi {
    // The existing backend exposes health outside /api/v1.
    @GET("/health")
    suspend fun health(): HealthResponse
}

@Serializable
data class LoginRequest(
    val username: String,
    val password: String,
    @SerialName("captcha_token") val captchaToken: String? = null,
    @SerialName("captcha_text") val captchaText: String? = null,
)

@Serializable
data class CurrentUserResponse(
    val id: Long,
    val username: String,
    val name: String,
    @SerialName("role_code") val roleCode: String,
    val permissions: List<String> = emptyList(),
    @SerialName("permission_version") val permissionVersion: Int = 1,
)

@Serializable
data class CaptchaChallengeResponse(
    val token: String,
    val image: String,
    @SerialName("expires_in_seconds") val expiresInSeconds: Int,
)

interface AuthApi {
    @GET("/api/v1/auth/me")
    suspend fun me(): CurrentUserResponse

    @POST("/api/v1/auth/login")
    suspend fun login(@Body request: LoginRequest): CurrentUserResponse

    @GET("/api/v1/auth/captcha")
    suspend fun captcha(): CaptchaChallengeResponse

    @POST("/api/v1/auth/logout")
    suspend fun logout()
}

@Serializable
data class AssistantConversationCreateRequest(val title: String? = null)

@Serializable
data class AssistantConversationMessageRequest(
    val content: String,
    @SerialName("version_no") val versionNo: Int,
)

@Serializable
data class AssistantMessageResponse(
    val id: Long,
    val role: String,
    val content: String,
    @SerialName("llm_elapsed_ms") val elapsedMs: Long? = null,
    @SerialName("created_at") val createdAt: String,
)

@Serializable
data class AssistantConversationResponse(
    @SerialName("conversation_id") val conversationId: String,
    val title: String? = null,
    @SerialName("version_no") val versionNo: Int,
    @SerialName("active_session_id") val activeSessionId: String? = null,
    val messages: List<AssistantMessageResponse> = emptyList(),
)

@Serializable
data class AssistantConversationSummary(
    @SerialName("conversation_id") val conversationId: String,
    @SerialName("first_user_message") val firstUserMessage: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
)

interface AssistantApi {
    @POST("/api/v1/assistant/conversations")
    suspend fun createConversation(
        @Body request: AssistantConversationCreateRequest = AssistantConversationCreateRequest(),
    ): AssistantConversationResponse

    @GET("/api/v1/assistant/conversations")
    suspend fun conversations(): List<AssistantConversationSummary>

    @GET("/api/v1/assistant/conversations/{conversationId}")
    suspend fun conversation(
        @Path("conversationId") conversationId: String,
    ): AssistantConversationResponse

    @POST("/api/v1/assistant/conversations/{conversationId}/messages")
    suspend fun sendMessage(
        @Path("conversationId") conversationId: String,
        @Body request: AssistantConversationMessageRequest,
    ): AssistantConversationResponse

    @DELETE("/api/v1/assistant/conversations/{conversationId}")
    suspend fun deleteConversation(@Path("conversationId") conversationId: String)
}

data class CityBondServices(
    val auth: AuthApi,
    val assistant: AssistantApi,
)

private class SessionCookieJar : CookieJar {
    private val cookies = mutableListOf<Cookie>()

    @Synchronized
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookies.forEach { incoming ->
            this.cookies.removeAll { current ->
                current.name == incoming.name && current.domain == incoming.domain && current.path == incoming.path
            }
            if (incoming.expiresAt > System.currentTimeMillis()) this.cookies += incoming
        }
    }

    @Synchronized
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        cookies.removeAll { it.expiresAt <= System.currentTimeMillis() }
        return cookies.filter { it.matches(url) }
    }

    @Synchronized
    fun clear() = cookies.clear()
}

object NetworkClient {
    const val DEFAULT_BASE_URL = "http://127.0.0.1:18080/"
    private val json = Json { ignoreUnknownKeys = true }
    private val sessionCookies = SessionCookieJar()
    // Reuse the connection pool. No body logging, credentials or business-write retries.
    private val client = OkHttpClient.Builder()
        .cookieJar(sessionCookies)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .callTimeout(20, TimeUnit.SECONDS)
        .retryOnConnectionFailure(false)
        .followRedirects(false)
        .followSslRedirects(false)
        .build()

    private val assistantClient = client.newBuilder()
        .readTimeout(90, TimeUnit.SECONDS)
        .callTimeout(100, TimeUnit.SECONDS)
        .build()

    fun create(baseUrl: String): HealthApi {
        return retrofit(validatedRoot(baseUrl), client).create(HealthApi::class.java)
    }

    fun createCityBond(baseUrl: String = DEFAULT_BASE_URL): CityBondServices {
        val retrofit = retrofit(validatedRoot(baseUrl), assistantClient)
        return CityBondServices(
            auth = retrofit.create(AuthApi::class.java),
            assistant = retrofit.create(AssistantApi::class.java),
        )
    }

    fun clearSession() {
        sessionCookies.clear()
        client.connectionPool.evictAll()
    }

    private fun retrofit(url: HttpUrl, httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(url)
            .client(httpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    private fun validatedRoot(baseUrl: String): HttpUrl {
        val url = baseUrl.trim().toHttpUrl()
        require(url.username.isEmpty() && url.password.isEmpty()) { "服务地址不能包含账号密码" }
        require(url.query == null && url.fragment == null && url.encodedPath == "/") {
            "请输入服务根地址，例如 $DEFAULT_BASE_URL"
        }
        return url
    }
}
