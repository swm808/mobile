package com.citybond.mobile.network

import java.util.concurrent.TimeUnit
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET

@Serializable
data class HealthResponse(val status: String)

interface HealthApi {
    // The existing backend exposes health outside /api/v1.
    @GET("/health")
    suspend fun health(): HealthResponse
}

object NetworkClient {
    private val json = Json { ignoreUnknownKeys = true }
    // Reuse the connection pool. No body logging, credentials or business-write retries.
    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .callTimeout(20, TimeUnit.SECONDS)
        .retryOnConnectionFailure(false)
        .followRedirects(false)
        .followSslRedirects(false)
        .build()

    fun create(baseUrl: String): HealthApi {
        val url = baseUrl.trim().toHttpUrl()
        require(url.username.isEmpty() && url.password.isEmpty()) { "服务地址不能包含账号密码" }
        require(url.query == null && url.fragment == null && url.encodedPath == "/") {
            "请输入服务根地址，例如 http://127.0.0.1:18080/"
        }
        return Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(HealthApi::class.java)
    }
}
