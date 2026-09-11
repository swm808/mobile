package com.citybond.mobile.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.citybond.mobile.model.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.format.DateTimeParseException

@Composable fun DebtListScreen(records: List<DebtUiRecord>, openDebt: (String) -> Unit, createDebt: () -> Unit,
    openIncomplete: () -> Unit) {
    var query by remember { mutableStateOf("") }
    var status by remember { mutableStateOf<DebtStatus?>(null) }
    var kind by remember { mutableStateOf<FinancingKind?>(null) }
    var message by remember { mutableStateOf<String?>(null) }
    val filtered = records.filter { record ->
        val keyword = query.trim()
        (keyword.isBlank() || listOf(record.number, record.projectName, record.debtorNames.joinToString(),
            record.creditorNames.joinToString()).any { it.contains(keyword, true) }) &&
            (status == null || record.debt.status == status) && (kind == null || record.debt.kind == kind)
    }
    Column(Modifier.fillMaxSize()) {
        Column(Modifier.padding(horizontal = 20.dp, vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) { Text("债务管理", style = MaterialTheme.typography.headlineSmall); Text("共 ${filtered.size} 笔债务", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                IconButton({ message = "导出功能将在接口对接后启用" }) { Icon(Icons.Outlined.KeyboardArrowDown, "导出") }
            }
            OutlinedTextField(query, { query = it }, Modifier.fillMaxWidth().testTag("debt_search"), singleLine = true,
                label = { Text("搜索编号、项目、债务人或债权人") }, leadingIcon = { Icon(Icons.Outlined.Search, null) },
                trailingIcon = { if (query.isNotEmpty()) IconButton({ query = "" }) { Icon(Icons.Outlined.Clear, "清空") } })
            Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(status == null, { status = null }, label = { Text("全部") })
                DebtStatus.entries.forEach { value -> FilterChip(status == value, { status = value }, label = { Text(value.displayName) }) }
            }
            Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(kind == null, { kind = null }, label = { Text("全部融资类型") })
                FinancingKind.entries.forEach { value -> FilterChip(kind == value, { kind = value }, label = { Text(value.displayName) }) }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(openIncomplete, Modifier.weight(1f).testTag("open_incomplete_debts")) { Text("待完善 ${records.count { it.debt.status == DebtStatus.INCOMPLETE }}") }
                Button(createDebt, Modifier.weight(1f).testTag("create_debt")) { Icon(Icons.Outlined.Add, null); Spacer(Modifier.width(6.dp)); Text("新增债务") }
            }
            message?.let { AssistChip({ message = null }, label = { Text(it) }, trailingIcon = { Icon(Icons.Outlined.Close, "关闭") }) }
        }
        if (filtered.isEmpty()) Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("没有符合条件的债务") }
        else LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(filtered, key = { it.debt.id }) { record -> DebtRecordCard(record) { openDebt(record.debt.id) } }
        }
    }
}

@Composable fun IncompleteDebtScreen(records: List<DebtUiRecord>, editDebt: (String) -> Unit) = PageContent {
    Text("待完善债务", style = MaterialTheme.typography.headlineSmall)
    Text("补齐债权人、合同、期限和还本付息信息后可转为正式债务。", color = MaterialTheme.colorScheme.onSurfaceVariant)
    val incomplete = records.filter { it.debt.status == DebtStatus.INCOMPLETE }
    if (incomplete.isEmpty()) Text("当前没有待完善记录")
    incomplete.forEach { record ->
        DebtRecordCard(record) { editDebt(record.debt.id) }
        Button({ editDebt(record.debt.id) }, Modifier.fillMaxWidth()) { Icon(Icons.Outlined.Edit, null); Spacer(Modifier.width(8.dp)); Text("继续完善") }
    }
}

private enum class DebtDetailTab(val label: String) { OVERVIEW("概览"), GUARANTEE("担保"), REPAYMENT("还本付息"), CALCULATION("成本测算") }

@Composable fun DebtDetailScreen(record: DebtUiRecord?, editDebt: (String) -> Unit) {
    if (record == null) return PageContent { Text("债务不存在", style = MaterialTheme.typography.headlineSmall) }
    var tab by remember { mutableStateOf(DebtDetailTab.OVERVIEW) }
    var message by remember { mutableStateOf<String?>(null) }
    Column(Modifier.fillMaxSize()) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) { Text(record.projectName, style = MaterialTheme.typography.titleLarge); Text(record.number, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                DebtStatusBadge(record.debt.status)
                IconButton({ editDebt(record.debt.id) }, Modifier.testTag("edit_debt")) { Icon(Icons.Outlined.Edit, "编辑债务") }
            }
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("附件 ${record.debt.attachmentIds.size}", "历史 v${record.version}", "删除").forEach { label -> OutlinedButton({ message = "$label：正式接口接入后可执行" }) { Text(label) } }
            }
            message?.let { Text(it, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary) }
        }
        PrimaryTabRow(tab.ordinal) { DebtDetailTab.entries.forEach { item -> Tab(tab == item, { tab = item }, text = { Text(item.label) }) } }
        PageContent {
            when (tab) {
                DebtDetailTab.OVERVIEW -> {
                    ProjectSection("债务基本信息") { ProjectInfoRow("债务类型", record.debtType); ProjectInfoRow("融资类型", record.debt.kind.displayName); ProjectInfoRow("合同编号", record.contractNumber ?: "待完善"); ProjectInfoRow("合同金额", record.debt.contractAmount.yuan.asCurrency()); ProjectInfoRow("放款金额", record.disbursementAmount.yuan.asCurrency()); ProjectInfoRow("本金余额", record.debt.outstandingPrincipal.yuan.asCurrency()) }
                    ProjectSection("业务关联") { ProjectInfoRow("债务人", record.debtorNames.joinToString("、")); ProjectInfoRow("债权人", record.creditorNames.joinToString("、").ifBlank { "待完善" }); ProjectInfoRow("借款日", record.debt.startDate?.toString() ?: "待完善"); ProjectInfoRow("到期日", record.debt.maturityDate?.toString() ?: "待完善") }
                }
                DebtDetailTab.GUARANTEE -> ProjectSection("担保信息") { if (record.guaranteeNames.isEmpty()) Text("无担保信息") else record.guaranteeNames.forEach { Text("• $it") } }
                DebtDetailTab.REPAYMENT -> ProjectSection("还本付息") { ProjectInfoRow("合同年利率", record.debt.annualRate?.multiply(BigDecimal("100")).asRate()); ProjectInfoRow("利率类型", record.rateType); ProjectInfoRow("还本方式", record.principalMethod); ProjectInfoRow("付息方式", record.interestMethod); ProjectInfoRow("还款主体", record.repaymentSubject ?: "待完善"); ProjectInfoRow("收款账户", record.receiptAccount ?: "待完善") }
                DebtDetailTab.CALCULATION -> ProjectSection("综合成本") { ProjectInfoRow("加权成本", record.weightedRatePercent.asRate()); ProjectInfoRow("IRR", record.irrPercent.asRate()); ProjectInfoRow("XIRR", record.xirrPercent.asRate()); Text("正式现金流接口接入后可重新测算。", color = MaterialTheme.colorScheme.onSurfaceVariant) }
            }
        }
    }
}

private data class DebtFormErrors(val project: String? = null, val debtor: String? = null, val creditor: String? = null,
    val contract: String? = null, val amount: String? = null, val disbursement: String? = null, val rate: String? = null,
    val start: String? = null, val maturity: String? = null)
private enum class DebtFormStep(val label: String) { BASIC("基础信息"), GUARANTEE("担保信息"), REPAYMENT("还本付息") }

@Composable fun DebtFormScreen(existing: DebtUiRecord?, onSave: (DebtUiRecord) -> Unit) {
    var step by remember { mutableStateOf(DebtFormStep.BASIC) }
    var project by remember { mutableStateOf(existing?.projectName.orEmpty()) }; var debtor by remember { mutableStateOf(existing?.debtorNames?.joinToString("、").orEmpty()) }
    var creditor by remember { mutableStateOf(existing?.creditorNames?.joinToString("、").orEmpty()) }; var contract by remember { mutableStateOf(existing?.contractNumber.orEmpty()) }
    var amount by remember { mutableStateOf(existing?.debt?.contractAmount?.yuan?.toPlainString().orEmpty()) }; var disbursement by remember { mutableStateOf(existing?.disbursementAmount?.yuan?.toPlainString().orEmpty()) }
    var start by remember { mutableStateOf(existing?.debt?.startDate?.toString().orEmpty()) }; var maturity by remember { mutableStateOf(existing?.debt?.maturityDate?.toString().orEmpty()) }
    var kind by remember { mutableStateOf(existing?.debt?.kind ?: FinancingKind.LOAN) }; var debtType by remember { mutableStateOf(existing?.debtType ?: "主债务") }
    var guarantees by remember { mutableStateOf(existing?.guaranteeNames?.joinToString("、").orEmpty()) }; var rate by remember { mutableStateOf(existing?.debt?.annualRate?.multiply(BigDecimal("100"))?.stripTrailingZeros()?.toPlainString().orEmpty()) }
    var rateType by remember { mutableStateOf(existing?.rateType ?: "固定利率") }; var principalMethod by remember { mutableStateOf(existing?.principalMethod ?: "到期还本") }
    var interestMethod by remember { mutableStateOf(existing?.interestMethod ?: "按季付息") }; var repaymentSubject by remember { mutableStateOf(existing?.repaymentSubject.orEmpty()) }; var account by remember { mutableStateOf(existing?.receiptAccount.orEmpty()) }
    var errors by remember { mutableStateOf(DebtFormErrors()) }
    fun submit() {
        val amountValue = amount.toBigDecimalOrNull(); val disbursementValue = disbursement.toBigDecimalOrNull(); val rateValue = rate.toBigDecimalOrNull()
        fun dateOf(text: String) = try { text.trim().takeIf { it.isNotEmpty() }?.let(LocalDate::parse) } catch (_: DateTimeParseException) { null }
        val startDate = dateOf(start); val maturityDate = dateOf(maturity)
        val next = DebtFormErrors(if (project.isBlank()) "请选择关联项目" else null, if (debtor.isBlank()) "请输入债务人" else null,
            if (creditor.isBlank()) "至少填写一个债权人" else null, if (contract.isBlank()) "请输入合同编号" else null,
            if (amountValue == null || amountValue <= BigDecimal.ZERO) "合同金额必须大于 0" else null,
            if (disbursementValue == null || disbursementValue < BigDecimal.ZERO || amountValue != null && disbursementValue > amountValue) "放款金额应在 0 至合同金额之间" else null,
            if (rateValue == null || rateValue < BigDecimal.ZERO || rateValue > BigDecimal("100")) "年利率应在 0—100 之间" else null,
            if (startDate == null) "请按 YYYY-MM-DD 填写借款日" else null,
            if (maturityDate == null || startDate != null && maturityDate < startDate) "到期日不得早于借款日" else null)
        errors = next
        if (next == DebtFormErrors()) {
            val id = existing?.debt?.id ?: "debt-local-${System.currentTimeMillis()}"; val creditorNames = creditor.split('、', ',', '，').map(String::trim).filter(String::isNotEmpty)
            val debt = Debt(id, existing?.debt?.projectId ?: "project-local", listOf("org-local"), creditorNames.indices.map { "creditor-local-$it" }, kind,
                Money(amountValue!!), Money(disbursementValue!!), rateValue!!.divide(BigDecimal("100"), 8, RoundingMode.HALF_UP), startDate, maturityDate,
                DebtStatus.ACTIVE, existing?.debt?.attachmentIds.orEmpty())
            onSave(DebtUiRecord(debt, existing?.number ?: "ZW-${LocalDate.now().year}-${id.takeLast(3)}", project.trim(), listOf(debtor.trim()), creditorNames,
                debtType, contract.trim(), Money(disbursementValue), rateType, principalMethod, interestMethod,
                guarantees.split('、', ',', '，').map(String::trim).filter(String::isNotEmpty), repaymentSubject.trim().ifBlank { null }, account.trim().ifBlank { null },
                existing?.weightedRatePercent, existing?.irrPercent, existing?.xirrPercent, (existing?.version ?: 0) + 1))
        } else step = DebtFormStep.BASIC
    }
    Column(Modifier.fillMaxSize()) {
        PrimaryTabRow(step.ordinal) { DebtFormStep.entries.forEach { item -> Tab(step == item, { step = item }, text = { Text(item.label) }) } }
        PageContent {
            Text(if (existing == null) "新增债务" else "编辑债务", style = MaterialTheme.typography.headlineSmall); Text("当前保存到运行期演示数据", color = MaterialTheme.colorScheme.primary)
            when (step) {
                DebtFormStep.BASIC -> {
                    DebtField(project, { project = it; errors = errors.copy(project = null) }, "关联项目 *", errors.project, "debt_project")
                    Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) { listOf("主债务", "附属债务", "附属费用").forEach { FilterChip(debtType == it, { debtType = it }, label = { Text(it) }) } }
                    Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) { FinancingKind.entries.forEach { item -> FilterChip(kind == item, { kind = item }, label = { Text(item.displayName) }) } }
                    DebtField(debtor, { debtor = it; errors = errors.copy(debtor = null) }, "债务人 *", errors.debtor, "debt_debtor")
                    DebtField(creditor, { creditor = it; errors = errors.copy(creditor = null) }, "债权人 *", errors.creditor, "debt_creditor", hint = "多个债权人用顿号或逗号分隔")
                    DebtField(contract, { contract = it; errors = errors.copy(contract = null) }, "合同编号 *", errors.contract, "debt_contract")
                    DebtField(amount, { amount = it; errors = errors.copy(amount = null) }, "合同金额（元）*", errors.amount, "debt_amount", KeyboardType.Decimal)
                    DebtField(disbursement, { disbursement = it; errors = errors.copy(disbursement = null) }, "放款金额（元）*", errors.disbursement, "debt_disbursement", KeyboardType.Decimal)
                    DebtField(start, { start = it; errors = errors.copy(start = null) }, "借款日 *", errors.start, "debt_start", hint = "YYYY-MM-DD")
                    DebtField(maturity, { maturity = it; errors = errors.copy(maturity = null) }, "到期日 *", errors.maturity, "debt_maturity", hint = "YYYY-MM-DD")
                }
                DebtFormStep.GUARANTEE -> { DebtField(guarantees, { guarantees = it }, "保证/抵押/质押", null, "debt_guarantee", hint = "多项用顿号或逗号分隔"); OutlinedCard(Modifier.fillMaxWidth()) { Text("合同与担保附件：正式文件服务接入后可上传", Modifier.padding(16.dp)) } }
                DebtFormStep.REPAYMENT -> {
                    DebtField(rate, { rate = it; errors = errors.copy(rate = null) }, "合同年利率（%）*", errors.rate, "debt_rate", KeyboardType.Decimal)
                    DebtField(rateType, { rateType = it }, "利率类型", null, "debt_rate_type"); DebtField(principalMethod, { principalMethod = it }, "还本方式", null, "debt_principal_method")
                    DebtField(interestMethod, { interestMethod = it }, "付息方式", null, "debt_interest_method"); DebtField(repaymentSubject, { repaymentSubject = it }, "还款主体", null, "debt_repayment_subject")
                    DebtField(account, { account = it }, "收款账户", null, "debt_account")
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                if (step != DebtFormStep.BASIC) OutlinedButton({ step = DebtFormStep.entries[step.ordinal - 1] }, Modifier.weight(1f)) { Text("上一步") }
                if (step != DebtFormStep.REPAYMENT) Button({ step = DebtFormStep.entries[step.ordinal + 1] }, Modifier.weight(1f)) { Text("下一步") }
                else Button(::submit, Modifier.weight(1f).testTag("save_debt")) { Text("保存债务") }
            }
        }
    }
}

@Composable private fun DebtField(value: String, change: (String) -> Unit, label: String, error: String?, tag: String,
    keyboardType: KeyboardType = KeyboardType.Text, hint: String? = null) = OutlinedTextField(value, change,
    Modifier.fillMaxWidth().testTag(tag), label = { Text(label) }, singleLine = true, isError = error != null,
    keyboardOptions = KeyboardOptions(keyboardType = keyboardType), supportingText = { (error ?: hint)?.let { Text(it, Modifier.testTag("${tag}_support")) } })
