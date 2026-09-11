package com.citybond.mobile.ui

import androidx.compose.runtime.mutableStateListOf
import com.citybond.mobile.model.Money
import com.citybond.mobile.model.Project
import com.citybond.mobile.model.ProjectStage
import java.math.BigDecimal
import java.time.LocalDate

data class ProjectDebtSummary(
    val name: String,
    val creditor: String,
    val amount: Money,
    val supplementary: Boolean = false,
)

data class ProjectCollateral(
    val kind: String,
    val name: String,
    val plannedValue: Money,
)

data class ProjectTimelineItem(val date: LocalDate, val title: String, val description: String)

data class ProjectUiRecord(
    val project: Project,
    val code: String,
    val fiscalYear: Int,
    val debtorName: String,
    val creditors: List<String>,
    val rateCapPercent: BigDecimal,
    val weightedRatePercent: BigDecimal?,
    val irrPercent: BigDecimal?,
    val xirrPercent: BigDecimal?,
    val contractPurpose: String?,
    val debts: List<ProjectDebtSummary>,
    val collaterals: List<ProjectCollateral>,
    val timeline: List<ProjectTimelineItem>,
    val version: Int,
)

internal fun demoProjectRecords() = mutableStateListOf(
    ProjectUiRecord(
        project = Project("project-001", "2026年演示城建集团·演示银行融资项目", "org-001", "user-001",
            Money(BigDecimal("10000000")), ProjectStage.FINANCING, LocalDate.parse("2026-09-16"), "重点推进项目"),
        code = "XM-2026-001", fiscalYear = 2026, debtorName = "演示城建集团", creditors = listOf("演示银行"),
        rateCapPercent = BigDecimal("5.00"), weightedRatePercent = BigDecimal("3.56"),
        irrPercent = BigDecimal("3.71"), xirrPercent = BigDecimal("3.68"), contractPurpose = "园区基础设施建设",
        debts = listOf(ProjectDebtSummary("流动资金贷款", "演示银行", Money(BigDecimal("8000000"))),
            ProjectDebtSummary("评估服务费", "演示咨询公司", Money(BigDecimal("80000")), true)),
        collaterals = listOf(ProjectCollateral("不动产权", "演示产权证 001", Money(BigDecimal("12000000")))),
        timeline = listOf(ProjectTimelineItem(LocalDate.parse("2026-09-09"), "进入融资阶段", "复核通过，开始推进融资"),
            ProjectTimelineItem(LocalDate.parse("2026-08-18"), "建立接洽", "与演示银行建立初步接洽")), version = 3,
    ),
    ProjectUiRecord(
        project = Project("project-002", "2026年老城区综合提升项目", "org-001", "user-001",
            Money(BigDecimal("2500000")), ProjectStage.NEGOTIATING, null, null),
        code = "XM-2026-002", fiscalYear = 2026, debtorName = "演示城建集团", creditors = listOf("演示银行", "演示租赁"),
        rateCapPercent = BigDecimal("5.00"), weightedRatePercent = null, irrPercent = null, xirrPercent = null,
        contractPurpose = "老城区公共基础设施提升", debts = emptyList(), collaterals = emptyList(),
        timeline = listOf(ProjectTimelineItem(LocalDate.parse("2026-09-01"), "项目创建", "等待债权机构反馈")), version = 1,
    ),
    ProjectUiRecord(
        project = Project("project-003", "2025年已完成融资项目", "org-001", "user-002",
            Money(BigDecimal("1000000")), ProjectStage.COMPLETED, null, "已结清示例"),
        code = "XM-2025-008", fiscalYear = 2025, debtorName = "演示产业集团", creditors = listOf("演示银行"),
        rateCapPercent = BigDecimal("4.80"), weightedRatePercent = BigDecimal("3.10"), irrPercent = BigDecimal("3.22"),
        xirrPercent = BigDecimal("3.18"), contractPurpose = "补充营运资金", debts = emptyList(), collaterals = emptyList(),
        timeline = listOf(ProjectTimelineItem(LocalDate.parse("2026-01-01"), "融资完成", "项目已全部结清")), version = 4,
    ),
)
