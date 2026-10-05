package com.citybond.mobile.network

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerializationException
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class NetworkClientTest {
    private lateinit var server: MockWebServer

    @Before fun start() {
        NetworkClient.clearSession()
        server = MockWebServer().also { it.start() }
    }
    @After fun stop() {
        NetworkClient.clearSession()
        server.shutdown()
    }

    @Test fun healthReadsBackendContractAndIgnoresExtraFields() = runBlocking {
        server.enqueue(MockResponse().setBody("""{"status":"ok","version":"future"}"""))
        assertEquals("ok", NetworkClient.create(server.url("/").toString()).health().status)
        val request = server.takeRequest()
        assertEquals("GET", request.method)
        assertEquals("/health", request.path)
        assertNull(request.getHeader("Authorization"))
        assertEquals(0L, request.bodySize)
    }

    @Test fun serverFailureIsNotRetriedOrReportedAsHealthy() {
        server.enqueue(MockResponse().setResponseCode(503).setBody("maintenance"))
        val error = assertThrows(HttpException::class.java) {
            runBlocking { NetworkClient.create(server.url("/").toString()).health() }
        }
        assertEquals(503, error.code())
        assertEquals(1, server.requestCount)
    }

    @Test fun missingStatusIsRejected() {
        server.enqueue(MockResponse().setBody("""{"message":"not a health response"}"""))
        assertThrows(SerializationException::class.java) {
            runBlocking { NetworkClient.create(server.url("/").toString()).health() }
        }
    }

    @Test fun credentialsAndNonRootAddressesAreRejected() {
        listOf("https://user:secret@example.com/", "https://example.com/api/v1/", "https://example.com/?token=x")
            .forEach { address -> assertThrows(IllegalArgumentException::class.java) { NetworkClient.create(address) } }
    }

    @Test fun loginCookieIsReusedForAssistantHistory() = runBlocking {
        server.enqueue(
            MockResponse()
                .setHeader("Set-Cookie", "citybond_session=session-123; Path=/; HttpOnly")
                .setBody(
                    """{"id":1,"username":"admin","name":"管理员","role_code":"super_admin","permissions":["assistant.view"],"permission_version":2}""",
                ),
        )
        server.enqueue(MockResponse().setBody("[]"))
        val services = NetworkClient.createCityBond(server.url("/").toString())

        val user = services.auth.login(LoginRequest("admin", "password"))
        assertEquals("管理员", user.name)
        assertTrue(services.assistant.conversations().isEmpty())

        val login = server.takeRequest()
        assertEquals("/api/v1/auth/login", login.path)
        assertTrue(login.body.readUtf8().contains("\"username\":\"admin\""))
        val history = server.takeRequest()
        assertEquals("/api/v1/assistant/conversations", history.path)
        assertEquals("citybond_session=session-123", history.getHeader("Cookie"))
    }

    @Test fun assistantConversationUsesServerVersionContract() = runBlocking {
        server.enqueue(
            MockResponse().setBody(
                """{"conversation_id":"c-1","version_no":1,"messages":[]}""",
            ),
        )
        server.enqueue(
            MockResponse().setBody(
                """{"conversation_id":"c-1","title":"融资问题","version_no":2,"messages":[{"id":1,"role":"user","content":"什么是综合成本？","created_at":"2026-09-23T00:00:00Z"},{"id":2,"role":"assistant","content":"综合成本用于衡量融资的整体资金成本。","llm_elapsed_ms":1200,"created_at":"2026-09-23T00:00:01Z"}]}""",
            ),
        )
        val api = NetworkClient.createCityBond(server.url("/").toString()).assistant

        val conversation = api.createConversation()
        val response = api.sendMessage(
            conversation.conversationId,
            AssistantConversationMessageRequest("什么是综合成本？", conversation.versionNo),
        )

        assertEquals(2, response.versionNo)
        assertEquals("assistant", response.messages.last().role)
        assertEquals("/api/v1/assistant/conversations", server.takeRequest().path)
        val messageRequest = server.takeRequest()
        assertEquals("/api/v1/assistant/conversations/c-1/messages", messageRequest.path)
        assertTrue(messageRequest.body.readUtf8().contains("\"version_no\":1"))
    }
}
