package com.citybond.mobile

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.citybond.mobile.network.NetworkClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExternalResource
import org.junit.rules.RuleChain

class NavigationTest {
    private val server = MockWebServer()
    private val serverRule = object : ExternalResource() {
        override fun before() {
            NetworkClient.clearSession()
            server.dispatcher = object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse = when (request.path) {
                    "/api/v1/auth/me" -> jsonResponse(
                        """{"id":1,"username":"tester","name":"测试用户","role_code":"super_admin","permissions":["assistant.view"]}""",
                    )
                    "/api/v1/assistant/conversations" -> jsonResponse("[]")
                    "/health" -> jsonResponse("""{"status":"ok"}""")
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

    @Before
    fun waitForAuthenticatedHome() {
        compose.waitUntil(timeoutMillis = 5_000) {
            compose.onAllNodes(hasText("你的移动工作台")).fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test fun tabsAndNestedRouteRemainNavigable() {
        compose.onNodeWithText("你的移动工作台").assertIsDisplayed()
        compose.onNodeWithText("进入业务").performClick()
        compose.onNodeWithText("最新 develop 功能已同步").assertIsDisplayed()
        compose.onNodeWithTag("contract_module_financing-overview").assertIsDisplayed()
        compose.onNodeWithTag("assistant_fab").performClick()
        compose.onNodeWithTag("assistant_quick_panel").assertIsDisplayed()
        compose.onNodeWithText("完整页面").performClick()
        compose.onNodeWithTag("assistant_screen").assertIsDisplayed()
        compose.onNodeWithTag("assistant_fab").assertDoesNotExist()
        compose.onNodeWithTag("tab_tasks").performClick()
        compose.onNodeWithText("任务服务尚未接入").assertIsDisplayed()
        compose.onNodeWithTag("tab_profile").performClick()
        compose.onNodeWithTag("open_connection").performClick()
        compose.onNodeWithTag("server_address").assertIsDisplayed()
        compose.onNodeWithContentDescription("返回").performClick()
        compose.onNodeWithText("测试用户，欢迎使用 CityBond").assertIsDisplayed()
        repeat(3) { compose.onNodeWithTag("tab_home").performClick() }
        compose.onNodeWithText("你的移动工作台").assertIsDisplayed()
    }

    @Test fun latestDevelopCombinedLedgerRouteIsNavigable() {
        compose.onNodeWithTag("tab_business").performClick()
        compose.onNodeWithTag("contract_module_supplementary-debt-fees").performScrollTo().performClick()
        compose.onNodeWithTag("combined_ledger").assertIsDisplayed()
        compose.onNodeWithText("按机构汇总").performScrollTo().performClick()
        compose.onNodeWithText("暂无符合条件的机构汇总").performScrollTo().assertIsDisplayed()
        compose.onNodeWithText("重置").performScrollTo().performClick()
    }

    @Test fun financingOverviewAndDetailRoutesRemainNavigable() {
        compose.onNodeWithTag("tab_business").performClick()
        compose.onNodeWithTag("contract_module_financing-overview").performClick()
        compose.onNodeWithText("界面与路由已就绪").assertIsDisplayed()

        compose.onNodeWithTag("open_financing_announcements").performClick()
        compose.onNodeWithTag("financing_announcements").assertIsDisplayed()
        compose.onNodeWithContentDescription("返回").performClick()

        compose.onNodeWithTag("open_financing_repayment_month").performScrollTo().performClick()
        compose.onNodeWithTag("financing_repayment_month").assertIsDisplayed()
        compose.onNodeWithContentDescription("返回").performClick()

        compose.onNodeWithTag("open_financing_daily").performScrollTo().performClick()
        compose.onNodeWithTag("financing_daily_disbursements").assertIsDisplayed()
        compose.onNodeWithContentDescription("返回").performClick()

        compose.onNodeWithTag("open_financing_pipeline").performScrollTo().performClick()
        compose.onNodeWithTag("financing_project_pipeline").assertIsDisplayed()
    }

    private fun jsonResponse(body: String) = MockResponse()
        .setResponseCode(200)
        .setHeader("Content-Type", "application/json")
        .setBody(body)
}
