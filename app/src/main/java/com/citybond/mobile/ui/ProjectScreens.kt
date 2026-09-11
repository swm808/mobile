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
import com.citybond.mobile.model.Money
import com.citybond.mobile.model.Project
import com.citybond.mobile.model.ProjectStage
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeParseException

@Composable fun BusinessScreen(openProjects: () -> Unit, openDebts: () -> Unit) = PageContent {
    Text("全部功能", style = MaterialTheme.typography.headlineSmall)
    Text("按业务模块查找功能，当前已接入项目管理演示。", color = MaterialTheme.colorScheme.onSurfaceVariant)
    OutlinedCard(onClick = openProjects, modifier = Modifier.fillMaxWidth().testTag("open_projects")) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("项目管理", style = MaterialTheme.typography.titleLarge); Text("台账、项目结构、进程与资料维护")
            Text("项目管理 · F03/F04", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
        }
    }
    OutlinedCard(onClick = openDebts, modifier = Modifier.fillMaxWidth().testTag("open_debts")) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("债务管理", style = MaterialTheme.typography.titleLarge); Text("债务台账、担保、还本付息与成本测算")
            Text("债务管理 · F05—F10", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun ProjectListScreen(records: List<ProjectUiRecord>, openProject: (String) -> Unit, createProject: () -> Unit) {
    var query by remember { mutableStateOf("") }
    var yearText by remember { mutableStateOf("") }
    var stage by remember { mutableStateOf<ProjectStage?>(null) }
    var message by remember { mutableStateOf<String?>(null) }
    val filtered = records.filter { record ->
        val keywordMatches = query.isBlank() || record.project.name.contains(query.trim(), true) || record.code.contains(query.trim(), true)
        val yearMatches = yearText.isBlank() || record.fiscalYear.toString() == yearText.trim()
        keywordMatches && yearMatches && (stage == null || record.project.stage == stage)
    }
    Scaffold(snackbarHost = { SnackbarHost(remember { SnackbarHostState() }) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            Column(Modifier.padding(horizontal = 20.dp, vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) { Text("项目管理", style = MaterialTheme.typography.headlineSmall); Text("共 ${filtered.size} 个项目", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                    IconButton({ message = "导入功能将在接口对接后启用" }) { Icon(Icons.Outlined.KeyboardArrowUp, "导入") }
                    IconButton({ message = "导出功能将在接口对接后启用" }) { Icon(Icons.Outlined.KeyboardArrowDown, "导出") }
                }
                OutlinedTextField(query, { query = it }, Modifier.fillMaxWidth().testTag("project_search"), singleLine = true,
                    label = { Text("搜索项目名称或编号") }, leadingIcon = { Icon(Icons.Outlined.Search, null) },
                    trailingIcon = { if (query.isNotEmpty()) IconButton({ query = "" }) { Icon(Icons.Outlined.Clear, "清空") } })
                OutlinedTextField(yearText, { yearText = it.filter(Char::isDigit).take(4) }, Modifier.fillMaxWidth(), singleLine = true,
                    label = { Text("所属年份") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(stage == null, { stage = null }, label = { Text("全部") })
                    ProjectStage.entries.forEach { value -> FilterChip(stage == value, { stage = value }, label = { Text(value.displayName) }) }
                }
                Button(createProject, Modifier.fillMaxWidth().testTag("create_project")) { Icon(Icons.Outlined.Add, null); Spacer(Modifier.width(8.dp)); Text("新增项目") }
                message?.let { AssistChip({ message = null }, label = { Text(it) }, trailingIcon = { Icon(Icons.Outlined.Close, "关闭") }) }
            }
            if (filtered.isEmpty()) Box(Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) { Icon(Icons.Outlined.Search, null); Text("没有符合条件的项目") }
            } else LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(filtered, key = { it.project.id }) { record -> ProjectRecordCard(record) { openProject(record.project.id) } }
            }
        }
    }
}

private enum class DetailTab(val label: String) { OVERVIEW("概览"), STRUCTURE("项目结构"), COLLATERAL("抵押计划"), PROGRESS("项目进程") }

@Composable fun ProjectDetailScreen(record: ProjectUiRecord?, editProject: (String) -> Unit) {
    if (record == null) return PageContent { Text("项目不存在", style = MaterialTheme.typography.headlineSmall) }
    var tab by remember { mutableStateOf(DetailTab.OVERVIEW) }
    var actionMessage by remember { mutableStateOf<String?>(null) }
    Column(Modifier.fillMaxSize()) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) { Text(record.project.name, style = MaterialTheme.typography.titleLarge); Text(record.code, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                ProjectStageBadge(record.project.stage)
                IconButton({ editProject(record.project.id) }, Modifier.testTag("edit_project")) { Icon(Icons.Outlined.Edit, "编辑项目") }
            }
            Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("调整阶段", "融资完成", if (record.project.stage == ProjectStage.ABANDONED) "恢复项目" else "放弃项目", "查看历史").forEach { label ->
                    OutlinedButton({ actionMessage = "$label：正式接口接入后可执行" }) { Text(label) }
                }
            }
            actionMessage?.let { Text(it, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary) }
        }
        PrimaryTabRow(tab.ordinal) { DetailTab.entries.forEach { item -> Tab(tab == item, { tab = item }, text = { Text(item.label) }) } }
        PageContent {
            when (tab) {
                DetailTab.OVERVIEW -> ProjectOverview(record)
                DetailTab.STRUCTURE -> ProjectStructure(record)
                DetailTab.COLLATERAL -> ProjectCollateralContent(record)
                DetailTab.PROGRESS -> ProjectProgress(record)
            }
        }
    }
}

@Composable private fun ProjectOverview(record: ProjectUiRecord) {
    ProjectSection("基本信息") {
        ProjectInfoRow("所属年份", record.fiscalYear.toString()); ProjectInfoRow("债务单位", record.debtorName)
        ProjectInfoRow("项目债权人", record.creditors.joinToString("、"))
        ProjectInfoRow("复核日期", record.project.reviewDate?.toString() ?: "未设置"); ProjectInfoRow("版本", "v${record.version}")
    }
    ProjectSection("综合成本") {
        ProjectInfoRow("成本上限", record.rateCapPercent.asRate()); ProjectInfoRow("加权成本", record.weightedRatePercent.asRate(), record.weightedRatePercent?.let { it > record.rateCapPercent } == true)
        ProjectInfoRow("IRR", record.irrPercent.asRate(), record.irrPercent?.let { it > record.rateCapPercent } == true)
        ProjectInfoRow("XIRR", record.xirrPercent.asRate(), record.xirrPercent?.let { it > record.rateCapPercent } == true)
    }
    ProjectSection("合同与备注") { ProjectInfoRow("合同用途", record.contractPurpose ?: "未填写"); ProjectInfoRow("备注", record.project.note ?: "未填写") }
}

@Composable private fun ProjectStructure(record: ProjectUiRecord) = ProjectSection("融资与债务关联（${record.debts.size}）") {
    if (record.debts.isEmpty()) Text("该项目下暂无债务记录", color = MaterialTheme.colorScheme.onSurfaceVariant)
    record.debts.forEach { debt -> ListItem(headlineContent = { Text(debt.name) }, supportingContent = { Text(debt.creditor) },
        trailingContent = { Column(horizontalAlignment = Alignment.End) { Text(debt.amount.yuan.asCurrency()); if (debt.supplementary) Text("附属", color = MaterialTheme.colorScheme.primary) } }) }
}

@Composable private fun ProjectCollateralContent(record: ProjectUiRecord) = ProjectSection("拟抵押资产（${record.collaterals.size}）") {
    if (record.collaterals.isEmpty()) Text("暂无拟抵押不动产权或股权", color = MaterialTheme.colorScheme.onSurfaceVariant)
    record.collaterals.forEach { item -> ListItem(headlineContent = { Text(item.name) }, supportingContent = { Text(item.kind) }, trailingContent = { Text(item.plannedValue.yuan.asCurrency()) }) }
}

@Composable private fun ProjectProgress(record: ProjectUiRecord) = ProjectSection("接洽与状态历史（${record.timeline.size}）") {
    record.timeline.forEach { item -> ListItem(headlineContent = { Text(item.title) }, overlineContent = { Text(item.date.toString()) }, supportingContent = { Text(item.description) }) }
}

private data class FormErrors(val year: String? = null, val debtor: String? = null, val creditors: String? = null,
    val cap: String? = null, val date: String? = null)

@Composable fun ProjectFormScreen(existing: ProjectUiRecord?, onSave: (ProjectUiRecord) -> Unit) {
    var year by remember(existing?.project?.id) { mutableStateOf((existing?.fiscalYear ?: LocalDate.now().year).toString()) }
    var debtor by remember(existing?.project?.id) { mutableStateOf(existing?.debtorName.orEmpty()) }
    var creditors by remember(existing?.project?.id) { mutableStateOf(existing?.creditors?.joinToString("、").orEmpty()) }
    var cap by remember(existing?.project?.id) { mutableStateOf(existing?.rateCapPercent?.toPlainString() ?: "5.00") }
    var purpose by remember(existing?.project?.id) { mutableStateOf(existing?.contractPurpose.orEmpty()) }
    var date by remember(existing?.project?.id) { mutableStateOf(existing?.project?.reviewDate?.toString().orEmpty()) }
    var note by remember(existing?.project?.id) { mutableStateOf(existing?.project?.note.orEmpty()) }
    var errors by remember { mutableStateOf(FormErrors()) }
    val creditorList = creditors.split('、', ',', '，').map(String::trim).filter(String::isNotEmpty)
    val generatedName = if (year.length == 4 && debtor.isNotBlank() && creditorList.isNotEmpty()) "$year 年${debtor.trim()}·${creditorList.first()}融资项目" else "选择年份、债务单位和债权人后自动生成"
    fun submit() {
        val yearValue = year.toIntOrNull(); val capValue = cap.toBigDecimalOrNull()
        val dateValue = try { date.trim().takeIf(String::isNotEmpty)?.let(LocalDate::parse) } catch (_: DateTimeParseException) { null }
        val next = FormErrors(if (yearValue == null || yearValue !in 2000..3000) "年份应在 2000—3000 之间" else null,
            if (debtor.isBlank()) "请输入债务单位" else null, if (creditorList.isEmpty()) "至少填写一个债权人" else null,
            if (capValue == null || capValue < BigDecimal.ZERO || capValue > BigDecimal("100")) "请输入 0—100 之间的成本上限" else null,
            if (date.isNotBlank() && dateValue == null) "请按 YYYY-MM-DD 填写日期" else null)
        errors = next
        if (next == FormErrors()) {
            val id = existing?.project?.id ?: "project-local-${System.currentTimeMillis()}"
            val code = existing?.code ?: "XM-$year-${id.takeLast(3)}"
            val project = Project(id, generatedName, existing?.project?.debtorId ?: "org-local", existing?.project?.ownerUserId ?: "user-001",
                existing?.project?.targetAmount ?: Money(BigDecimal.ZERO), existing?.project?.stage ?: ProjectStage.NEGOTIATING, dateValue, note.trim().ifBlank { null })
            onSave(ProjectUiRecord(project, code, yearValue!!, debtor.trim(), creditorList, capValue!!, existing?.weightedRatePercent,
                existing?.irrPercent, existing?.xirrPercent, purpose.trim().ifBlank { null }, existing?.debts.orEmpty(),
                existing?.collaterals.orEmpty(), existing?.timeline.orEmpty(), (existing?.version ?: 0) + 1))
        }
    }
    PageContent {
        Text(if (existing == null) "新增项目" else "编辑项目", style = MaterialTheme.typography.headlineSmall)
        Text("当前保存到运行期演示数据", color = MaterialTheme.colorScheme.primary)
        OutlinedTextField(generatedName, {}, Modifier.fillMaxWidth(), label = { Text("项目名称") }, enabled = false)
        OutlinedTextField(existing?.code ?: "创建后自动生成", {}, Modifier.fillMaxWidth(), label = { Text("项目编号") }, enabled = false)
        ProjectFormField(year, { year = it.filter(Char::isDigit).take(4); errors = errors.copy(year = null) }, "所属年份 *", errors.year, "project_year", KeyboardType.Number)
        ProjectFormField(debtor, { debtor = it; errors = errors.copy(debtor = null) }, "债务单位 *", errors.debtor, "project_debtor")
        ProjectFormField(creditors, { creditors = it; errors = errors.copy(creditors = null) }, "项目债权人 *", errors.creditors, "project_creditors", hint = "多个债权人用顿号或逗号分隔")
        ProjectFormField(cap, { cap = it; errors = errors.copy(cap = null) }, "综合成本上限（%）*", errors.cap, "project_rate_cap", KeyboardType.Decimal)
        ProjectFormField(date, { date = it; errors = errors.copy(date = null) }, "复核日期", errors.date, "project_date", hint = "YYYY-MM-DD")
        OutlinedTextField(purpose, { purpose = it.take(300) }, Modifier.fillMaxWidth(), label = { Text("合同约定用途") }, minLines = 3, supportingText = { Text("${purpose.length}/300") })
        OutlinedTextField(note, { note = it }, Modifier.fillMaxWidth(), label = { Text("备注") }, minLines = 2)
        Button(::submit, Modifier.fillMaxWidth().testTag("save_project")) { Text(if (existing == null) "创建项目" else "保存修改") }
    }
}

@Composable private fun ProjectFormField(value: String, change: (String) -> Unit, label: String, error: String?, tag: String,
    keyboardType: KeyboardType = KeyboardType.Text, hint: String? = null) {
    OutlinedTextField(value, change, Modifier.fillMaxWidth().testTag(tag), label = { Text(label) }, singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType), isError = error != null,
        supportingText = { (error ?: hint)?.let { Text(it, Modifier.testTag("${tag}_support")) } })
}
