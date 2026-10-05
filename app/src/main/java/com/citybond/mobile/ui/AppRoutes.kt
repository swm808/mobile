package com.citybond.mobile.ui

import java.time.LocalDate
import java.time.YearMonth

object AppRoutes {
    private const val FINANCING_ROOT = "financing-overview"

    const val HOME = "home"
    const val BUSINESS = "business"
    const val ASSISTANT = "assistant"
    const val TASKS = "tasks"
    const val PROFILE = "profile"
    const val CONNECTION = "connection"
    const val COMBINED_LEDGER = "combined-ledger"

    const val FINANCING_OVERVIEW = FINANCING_ROOT
    const val FINANCING_ANNOUNCEMENTS = "$FINANCING_ROOT/announcements"
    const val FINANCING_PENDING_ITEMS = "$FINANCING_ROOT/pending-items"
    const val FINANCING_DAILY_DATE_ARGUMENT = "date"
    const val FINANCING_REPAYMENT_MONTH_ARGUMENT = "month"
    const val FINANCING_PIPELINE_ARGUMENT = "category"
    const val FINANCING_DAILY_DISBURSEMENTS =
        "$FINANCING_ROOT/daily-disbursements/{$FINANCING_DAILY_DATE_ARGUMENT}"
    const val FINANCING_REPAYMENT_MONTH =
        "$FINANCING_ROOT/repayments/{$FINANCING_REPAYMENT_MONTH_ARGUMENT}"
    const val FINANCING_PROJECT_PIPELINE =
        "$FINANCING_ROOT/projects/{$FINANCING_PIPELINE_ARGUMENT}"

    val topLevelRoutes = setOf(HOME, BUSINESS, ASSISTANT, TASKS, PROFILE)

    private val titles = mapOf(
        HOME to "首页",
        BUSINESS to "业务",
        ASSISTANT to "助手",
        TASKS to "任务",
        PROFILE to "我的",
        CONNECTION to "连接检查",
        COMBINED_LEDGER to "综合台账",
        FINANCING_OVERVIEW to "融资总览",
        FINANCING_ANNOUNCEMENTS to "公告栏",
        FINANCING_PENDING_ITEMS to "未处理事项",
        FINANCING_DAILY_DISBURSEMENTS to "每日融资到账",
        FINANCING_REPAYMENT_MONTH to "月份还款明细",
        FINANCING_PROJECT_PIPELINE to "融资项目进度",
    )

    fun titleFor(routePattern: String?): String = titles[routePattern] ?: "CityBond"

    fun financingDailyDisbursements(date: LocalDate): String =
        "$FINANCING_ROOT/daily-disbursements/$date"

    fun financingRepaymentMonth(month: YearMonth): String =
        "$FINANCING_ROOT/repayments/$month"

    fun financingProjectPipeline(category: FinancingPipelineRoute): String =
        "$FINANCING_ROOT/projects/${category.pathValue}"
}

enum class FinancingPipelineRoute(val pathValue: String, val label: String) {
    APPROVAL_PENDING("approval-pending", "待审批"),
    DISBURSEMENT_PENDING("disbursement-pending", "待放款"),
    IN_PROGRESS("in-progress", "融资中"),
    COMPLETED("completed", "已完成");

    companion object {
        fun fromPath(value: String?): FinancingPipelineRoute? =
            entries.firstOrNull { it.pathValue == value }
    }
}
