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
        compose.onNodeWithText("全部功能").assertIsDisplayed()
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

    @Test fun projectRoutesAndFormValidationWork() {
        compose.onNodeWithTag("tab_business").performClick()
        compose.onNodeWithTag("open_projects").performClick()
        compose.onNodeWithTag("project_search").assertIsDisplayed()
        compose.onNodeWithText("2026年演示城建集团·演示银行融资项目").performClick()
        compose.onNodeWithText("重点推进项目").assertExists()
        compose.onNodeWithTag("edit_project").performClick()
        compose.onNodeWithTag("project_debtor").assertTextContains("演示城建集团")
        compose.onNodeWithContentDescription("返回").performClick()
        compose.onNodeWithContentDescription("返回").performClick()
        compose.onNodeWithTag("create_project").performClick()
        compose.onNodeWithTag("save_project").performScrollTo().performClick()
        compose.onNodeWithTag("project_debtor_support", useUnmergedTree = true).assertTextContains("请输入债务单位")
        compose.onNodeWithTag("project_creditors_support", useUnmergedTree = true).assertTextContains("至少填写一个债权人")
    }

    @Test fun debtRoutesAndFormValidationWork() {
        compose.onNodeWithTag("tab_business").performClick()
        compose.onNodeWithTag("open_debts").performClick()
        compose.onNodeWithTag("debt_search").assertIsDisplayed()
        compose.onNodeWithText("2026年演示城建集团·演示银行融资项目").performClick()
        compose.onNodeWithText("ZW-2026-001").assertIsDisplayed()
        compose.onNodeWithTag("edit_debt").performClick()
        compose.onNodeWithTag("debt_contract").assertTextContains("HT-2026-001")
        compose.onNodeWithContentDescription("返回").performClick()
        compose.onNodeWithContentDescription("返回").performClick()
        compose.onNodeWithTag("create_debt").performClick()
        compose.onNodeWithText("还本付息").performClick()
        compose.onNodeWithTag("save_debt").performScrollTo().performClick()
        compose.onNodeWithTag("debt_project_support", useUnmergedTree = true).assertTextContains("请选择关联项目")
        compose.onNodeWithTag("debt_creditor_support", useUnmergedTree = true).assertTextContains("至少填写一个债权人")
    }
}
