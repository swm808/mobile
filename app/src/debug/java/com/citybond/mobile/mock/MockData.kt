package com.citybond.mobile.mock

import com.citybond.mobile.model.*
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate

// 纯内存、固定时间、虚构内容；不触发任何网络或磁盘写入。
object MockData {
    val referenceDate: LocalDate = LocalDate.parse("2026-09-09")
    private val createdAt = Instant.parse("2026-09-09T02:00:00Z")
    private fun money(value: String) = Money(BigDecimal(value))

    val organizations = listOf(
        Organization("org-001", "演示城建集团", OrganizationKind.DEBTOR),
        Organization("org-002", "演示银行", OrganizationKind.CREDITOR),
        Organization("org-003", "演示担保公司", OrganizationKind.GUARANTOR),
    )
    val users = listOf(
        User("user-001", "演示经办人", "org-001", setOf("业务经办"), true),
        User("user-002", "演示复核人", "org-001", setOf("业务复核"), true),
        User("user-003", "演示停用账号", "org-001", emptySet(), false),
    )
    val currentUser: User = users.first()

    val projects = listOf(
        Project("project-001", "演示园区一期", "org-001", "user-001",
            money("10000000.00"), ProjectStage.FINANCING, referenceDate.plusDays(7), "用于列表和详情测试"),
        Project("project-002", "演示老城区公共基础设施及配套服务综合提升二期项目（超长名称测试）",
            "org-001", "user-001", money("2500000.00"), ProjectStage.NEGOTIATING, null, null),
        Project("project-003", "演示已完成项目", "org-001", "user-002",
            money("1000000.00"), ProjectStage.COMPLETED, null, "已结清示例"),
    )
    val debts = listOf(
        Debt("debt-001", "project-001", listOf("org-001"), listOf("org-002"), FinancingKind.LOAN,
            money("10000000.00"), money("8000000.00"), BigDecimal("0.035"),
            LocalDate.parse("2026-01-01"), LocalDate.parse("2027-01-01"), DebtStatus.ACTIVE,
            listOf("attachment-001")),
        Debt("debt-002", "project-002", listOf("org-001"), emptyList(), FinancingKind.BOND,
            money("2500000.00"), money("0.00"), null, null, null, DebtStatus.INCOMPLETE, emptyList()),
        Debt("debt-003", "project-003", listOf("org-001"), listOf("org-002"), FinancingKind.LOAN,
            money("1000000.00"), money("0.00"), BigDecimal("0.03"),
            LocalDate.parse("2025-01-01"), LocalDate.parse("2026-01-01"), DebtStatus.SETTLED, emptyList()),
    )
    // 仅部分展示记录，不是完整还款计划；利息为固定示例值，不作为计算依据。
    val repayments = listOf(
        Repayment("repayment-001", "debt-001", LocalDate.parse("2026-08-01"), money("2000000.00"),
            money("25000.00"), PaymentStatus.PAID, LocalDate.parse("2026-08-01")),
        Repayment("repayment-002", "debt-001", referenceDate.plusDays(7), money("1000000.00"),
            money("23333.33"), PaymentStatus.UNPAID, null),
        Repayment("repayment-003", "debt-003", LocalDate.parse("2026-01-01"), money("1000000.00"),
            money("30000.00"), PaymentStatus.PAID, LocalDate.parse("2026-01-01")),
    )
    val cashEntries = listOf(
        CashEntry("cash-001", "debt-001", referenceDate.plusDays(7), CashEntryKind.PLANNED,
            CashDirection.EXPENSE, money("1023333.33"), "待还本息"),
        CashEntry("cash-002", "debt-001", LocalDate.parse("2026-01-01"), CashEntryKind.ACTUAL,
            CashDirection.INCOME, money("10000000.00"), "贷款到账"),
    )
    val attachments = listOf(
        Attachment("attachment-001", BusinessRef(BusinessKind.DEBT, "debt-001"),
            "演示借款合同.pdf", "application/pdf", 204800L, null),
    )
    val documents = listOf(
        BusinessDocument("document-001", "MOCK-HK-001", DocumentKind.REPAYMENT_NOTICE,
            BusinessRef(BusinessKind.REPAYMENT, "repayment-002"), "user-001", money("1023333.33"),
            DocumentStatus.PENDING, createdAt),
        BusinessDocument("document-002", "MOCK-FY-001", DocumentKind.FEE_APPLICATION,
            BusinessRef(BusinessKind.DEBT, "debt-001"), "user-001", money("5000.00"),
            DocumentStatus.DRAFT, createdAt),
    )
    val tasks = listOf(
        BusinessTask("task-001", "user-001", TaskKind.OCR, TaskStatus.RUNNING,
            BusinessRef(BusinessKind.DEBT, "debt-001"), 45, TaskCapabilities(true, false), null, null),
        BusinessTask("task-002", "user-001", TaskKind.EXPORT, TaskStatus.FAILED,
            BusinessRef(BusinessKind.PROJECT, "project-001"), null, TaskCapabilities(false, true),
            "演示错误：文件生成失败，请重试", null),
        BusinessTask("task-003", "user-002", TaskKind.FORM_FILL, TaskStatus.QUEUED,
            null, null, TaskCapabilities(false, false), null, null),
    )
    val conversations = listOf(
        Conversation("conversation-001", "user-001", "为园区项目补充融资信息", null, createdAt),
        Conversation("conversation-002", "user-002", "新会话（空消息测试）", null, createdAt),
    )
    val drafts = listOf(
        DebtDraft("draft-001", "conversation-001", "user-001", 2,
            "project-001", money("3000000.00"), null),
    )
    val messages = listOf(
        ChatMessage("message-001", "conversation-001", 1, MessageRole.USER,
            "为演示园区一期录入一笔贷款。", MessageStatus.COMPLETE, createdAt),
        ChatMessage("message-002", "conversation-001", 2, MessageRole.ASSISTANT,
            "贷款金额是多少？", MessageStatus.COMPLETE, createdAt.plusSeconds(1)),
        ChatMessage("message-003", "conversation-001", 3, MessageRole.USER,
            "300 万元。", MessageStatus.COMPLETE, createdAt.plusSeconds(10)),
        ChatMessage("message-004", "conversation-001", 4, MessageRole.ASSISTANT,
            "已记录 300 万元，请补充债权机构。尚未正式提交。", MessageStatus.COMPLETE,
            createdAt.plusSeconds(11), "draft-001"),
    )
    val emptyProjects: List<Project> = emptyList()
}
