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
        compose.onNodeWithText("业务入口").assertIsDisplayed()
        compose.onNodeWithTag("tab_assistant").performClick()
        compose.onNodeWithText("助手尚未接入").assertIsDisplayed()
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
}
