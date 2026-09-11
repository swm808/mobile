package com.citybond.mobile.ui

import androidx.compose.runtime.mutableStateListOf
import com.citybond.mobile.model.Debt
import com.citybond.mobile.model.DebtStatus
import com.citybond.mobile.model.FinancingKind
import com.citybond.mobile.model.Money
import java.math.BigDecimal
import java.time.LocalDate

data class DebtUiRecord(
    val debt: Debt,
    val number: String,
    val projectName: String,
    val debtorNames: List<String>,
    val creditorNames: List<String>,
    val debtType: String,
    val contractNumber: String?,
    val disbursementAmount: Money,
    val rateType: String,
    val principalMethod: String,
    val interestMethod: String,
    val guaranteeNames: List<String>,
    val repaymentSubject: String?,
    val receiptAccount: String?,
    val weightedRatePercent: BigDecimal?,
    val irrPercent: BigDecimal?,
    val xirrPercent: BigDecimal?,
    val version: Int,
)

internal fun demoDebtRecords() = mutableStateListOf(
    DebtUiRecord(
        Debt("debt-001", "project-001", listOf("org-001"), listOf("bank-001"), FinancingKind.LOAN,
            Money(BigDecimal("8000000")), Money(BigDecimal("6200000")), BigDecimal("0.035"),
            LocalDate.parse("2026-03-20"), LocalDate.parse("2029-03-20"), DebtStatus.ACTIVE,
            listOf("attachment-001", "attachment-002")),
        "ZW-2026-001", "2026年演示城建集团·演示银行融资项目", listOf("演示城建集团"), listOf("演示银行"),
        "主债务", "HT-2026-001", Money(BigDecimal("8000000")), "固定利率", "分期还本", "按季付息",
        listOf("演示担保集团保证", "演示产权证抵押"), "演示城建集团", "演示银行 8888", BigDecimal("3.56"),
        BigDecimal("3.71"), BigDecimal("3.68"), 3),
    DebtUiRecord(
        Debt("debt-002", "project-001", listOf("org-001"), listOf("consult-001"), FinancingKind.BILL,
            Money(BigDecimal("80000")), Money(BigDecimal.ZERO), null, LocalDate.parse("2026-03-15"),
            LocalDate.parse("2026-03-15"), DebtStatus.SETTLED, listOf("attachment-003")),
        "ZW-2026-002", "2026年演示城建集团·演示银行融资项目", listOf("演示城建集团"), listOf("演示咨询公司"),
        "附属费用", "FW-2026-008", Money(BigDecimal("80000")), "不适用", "一次支付", "不计息",
        emptyList(), "演示城建集团", null, null, null, null, 2),
    DebtUiRecord(
        Debt("debt-draft-001", "project-002", listOf("org-001"), emptyList(), FinancingKind.BOND,
            Money(BigDecimal("2500000")), Money(BigDecimal("2500000")), null, null, null,
            DebtStatus.INCOMPLETE, emptyList()),
        "待完善", "2026年老城区综合提升项目", listOf("演示城建集团"), emptyList(), "主债务", null,
        Money(BigDecimal.ZERO), "待填写", "待填写", "待填写", emptyList(), null, null, null, null, null, 1),
)
