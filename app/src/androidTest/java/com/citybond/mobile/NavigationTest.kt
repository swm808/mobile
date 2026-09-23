package com.citybond.mobile

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()

    @Test fun tabsAndNestedRouteRemainNavigable() {
        compose.onNodeWithText("你的移动工作台").assertIsDisplayed()
        compose.onNodeWithText("进入业务").performClick()
        compose.onNodeWithText("develop 业务合同已同步").assertIsDisplayed()
        compose.onNodeWithTag("contract_module_financing-overview").assertIsDisplayed()
        compose.onNodeWithTag("tab_assistant").performClick()
        compose.onNodeWithText("助手正在重新设计").assertIsDisplayed()
        compose.onNodeWithTag("tab_tasks").performClick()
        compose.onNodeWithText("任务服务尚未接入").assertIsDisplayed()
        compose.onNodeWithTag("tab_profile").performClick()
        compose.onNodeWithTag("open_connection").performClick()
        compose.onNodeWithTag("server_address").assertIsDisplayed()
        compose.onNodeWithContentDescription("返回").performClick()
        compose.onNodeWithText("欢迎使用 CityBond").assertIsDisplayed()
        repeat(3) { compose.onNodeWithTag("tab_home").performClick() }
        compose.onNodeWithText("你的移动工作台").assertIsDisplayed()
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
}
