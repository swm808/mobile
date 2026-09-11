package com.citybond.mobile.model

import java.math.BigDecimal
import java.time.LocalDate

// 移动端业务模型草案，不是后端 DTO；ID 和枚举需在正式对接时映射。
// 金额统一为人民币元，从十进制字符串构造，禁止经 Double 转换。
data class Money(val yuan: BigDecimal)

enum class OrganizationKind { DEBTOR, CREDITOR, GUARANTOR }

data class Organization(
    val id: String,
    val name: String,
    val kind: OrganizationKind,
    val parentId: String? = null,
)

data class User(
    val id: String,
    val displayName: String,
    val organizationId: String,
    val roleNames: Set<String>,
    val enabled: Boolean,
)

enum class ProjectStage { NEGOTIATING, FINANCING, COMPLETED, ABANDONED }

data class Project(
    val id: String,
    val name: String,
    val debtorId: String,
    val ownerUserId: String,
    val targetAmount: Money,
    val stage: ProjectStage,
    val reviewDate: LocalDate?,
    val note: String?,
)

enum class FinancingKind { LOAN, BOND, BILL }
enum class DebtStatus { INCOMPLETE, ACTIVE, SETTLED }

data class Debt(
    val id: String,
    val projectId: String,
    val debtorIds: List<String>,
    val creditorIds: List<String>,
    val kind: FinancingKind,
    val contractAmount: Money,
    val outstandingPrincipal: Money,
    // 年利率为比例值：0.035 = 3.5%。当前只建固定利率示例。
    val annualRate: BigDecimal?,
    val startDate: LocalDate?,
    val maturityDate: LocalDate?,
    val status: DebtStatus,
    val attachmentIds: List<String>,
)

enum class PaymentStatus { UNPAID, PAID, SKIPPED }

data class Repayment(
    val id: String,
    val debtId: String,
    val dueDate: LocalDate,
    val principal: Money,
    val interest: Money,
    val status: PaymentStatus,
    val paidDate: LocalDate?,
)

enum class CashEntryKind { PLANNED, ACTUAL }
enum class CashDirection { INCOME, EXPENSE }

data class CashEntry(
    val id: String,
    val debtId: String?,
    val date: LocalDate,
    val kind: CashEntryKind,
    val direction: CashDirection,
    val amount: Money,
    val note: String?,
)
