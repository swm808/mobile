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

    @Before fun start() { server = MockWebServer().also { it.start() } }
    @After fun stop() { server.shutdown() }

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
}
