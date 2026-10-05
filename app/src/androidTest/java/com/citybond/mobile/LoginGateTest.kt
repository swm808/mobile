package com.citybond.mobile

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.semantics.SemanticsActions
import com.citybond.mobile.network.NetworkClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExternalResource
import org.junit.rules.RuleChain

class LoginGateTest {
    private val server = MockWebServer()
    private val serverRule = object : ExternalResource() {
        override fun before() {
            NetworkClient.clearSession()
            server.dispatcher = object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse = when (request.path) {
                    "/api/v1/auth/me" -> MockResponse().setResponseCode(401)
                    "/api/v1/auth/login" -> jsonResponse(
                        """{"id":2,"username":"mobile","name":"移动用户","role_code":"user","permissions":["assistant.view"]}""",
                    )
                    "/api/v1/assistant/conversations" -> jsonResponse("[]")
                    else -> MockResponse().setResponseCode(404)
                }
            }
            server.start(18080)
        }

        override fun after() {
            server.shutdown()
        }
    }
    private val compose = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val rules: RuleChain = RuleChain.outerRule(serverRule).around(compose)

    @Test
    fun loginIsRequiredBeforeOpeningApp() {
        compose.onNodeWithTag("app_login").assertIsDisplayed()
        compose.onNodeWithTag("login_brand_icon").assertIsDisplayed()
        compose.onNodeWithTag("tab_home").assertDoesNotExist()

        compose.onNodeWithTag("login_username").performTextInput("mobile")
        compose.onNodeWithTag("login_password").performTextInput("password")
        compose.onNodeWithTag("login_submit")
            .assertIsEnabled()
            .performSemanticsAction(SemanticsActions.OnClick)
        compose.waitUntil(timeoutMillis = 5_000) {
            compose.onAllNodes(hasText("你的移动工作台")).fetchSemanticsNodes().isNotEmpty()
        }

        compose.onNodeWithText("你的移动工作台").assertIsDisplayed()
        compose.onNodeWithTag("app_login").assertDoesNotExist()
    }

    private fun jsonResponse(body: String) = MockResponse()
        .setResponseCode(200)
        .setHeader("Content-Type", "application/json")
        .setBody(body)
}
