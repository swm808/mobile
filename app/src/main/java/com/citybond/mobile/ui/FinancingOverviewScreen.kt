package com.citybond.mobile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

private enum class OverviewMoneyUnit(val label: String) {
    TEN_THOUSAND("万元"),
    HUNDRED_MILLION("亿元"),
}

@Composable
internal fun FinancingOverviewScreen(
    openAnnouncements: () -> Unit,
    openPendingItems: () -> Unit,
    openDailyDisbursements: (LocalDate) -> Unit,
    openRepaymentMonth: (YearMonth) -> Unit,
    openProjectPipeline: (FinancingPipelineRoute) -> Unit,
) = PageContent {
    val today = LocalDate.now()
    var moneyUnit by rememberSaveable { mutableStateOf(OverviewMoneyUnit.HUNDRED_MILLION) }
    var includeSupplementary by rememberSaveable { mutableStateOf(false) }

    PageIntro(
        eyebrow = "经营驾驶舱",
        title = "融资总览",
        description = "集团合并口径 · 数据截至 $today",
    )
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        TextButton(onClick = openAnnouncements, modifier = Modifier.testTag("open_financing_announcements")) {
            Text("公告栏")
        }
        TextButton(onClick = openPendingItems, modifier = Modifier.testTag("open_financing_pending")) {
            Text("未处理事项")
        }
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text("界面与路由已就绪", style = MaterialTheme.typography.titleMedium)
            Text(
                "当前尚未接入融资数据接口和分区权限展示，因此数值保持为空；接入后将按服务端口径展示。",
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
    }

    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("查询条件", style = MaterialTheme.typography.titleMedium)
            Text("主体：登录后按数据权限选择", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("截止日期：$today", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("债务范围", style = MaterialTheme.typography.labelMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = true, onClick = {}, label = { Text("主债务") })
                FilterChip(
                    selected = includeSupplementary,
                    onClick = { includeSupplementary = !includeSupplementary },
                    label = { Text("附属债务") },
                )
            }
            Text("金额单位", style = MaterialTheme.typography.labelMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OverviewMoneyUnit.entries.forEach { unit ->
                    FilterChip(
                        selected = moneyUnit == unit,
                        onClick = { moneyUnit = unit },
                        label = { Text(unit.label) },
                    )
                }
            }
            Button(onClick = {}, enabled = false, modifier = Modifier.fillMaxWidth()) {
                Text("等待权限与接口后查询")
            }
        }
    }

    SectionHeader("核心指标", "当前金额单位：${moneyUnit.label}")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        OverviewMetricCard(
            title = "${today.year}年度计划融资",
            note = "overview.view",
            modifier = Modifier.weight(1f),
        )
        OverviewMetricCard(
            title = "融资总额",
            note = "query.view",
            modifier = Modifier.weight(1f),
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        OverviewMetricCard(
            title = "存续债务余额",
            note = "query.view",
            modifier = Modifier.weight(1f),
        )
        OverviewMetricCard(
            title = "本月应还本息",
            note = "debt.view",
            modifier = Modifier.weight(1f),
        )
    }

    SectionHeader("业务视图", "按时间与项目维度继续查看明细")
    FinancingRouteCard(
        title = "未来 6 个月到期本息",
        description = "按月汇总本金与利息，并进入月份明细。",
        action = "查看本月",
        icon = Icons.AutoMirrored.Outlined.List,
        testTag = "open_financing_repayment_month",
        onClick = { openRepaymentMonth(YearMonth.from(today)) },
    )
    FinancingRouteCard(
        title = "每日融资到账",
        description = "区分计划放款与实际放款，金额由资金速记汇总。",
        action = "查看今日",
        icon = Icons.Outlined.Home,
        testTag = "open_financing_daily",
        onClick = { openDailyDisbursements(today) },
    )
    FinancingRouteCard(
        title = "融资项目进度",
        description = "查看待审批、待放款、融资中与已完成项目。",
        action = "查看待审批",
        icon = Icons.Outlined.Menu,
        testTag = "open_financing_pipeline",
        onClick = { openProjectPipeline(FinancingPipelineRoute.APPROVAL_PENDING) },
    )

    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("结构与变化", style = MaterialTheme.typography.titleMedium)
            ContractValueRow("债务余额结构", "等待 query.view")
            ContractValueRow("融资渠道变化", "等待 query.view")
            ContractValueRow("平均综合成本（XIRR）", "等待 query.view")
            ContractValueRow("项目阶段与融资品种分布", "等待真实数据")
        }
    }
}

@Composable
private fun OverviewMetricCard(
    title: String,
    note: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(title, style = MaterialTheme.typography.labelLarge)
            Text("—", style = MaterialTheme.typography.headlineMedium)
            StatusPill("等待数据", StatusTone.NEUTRAL)
            Text(note, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun FinancingRouteCard(
    title: String,
    description: String,
    action: String,
    icon: ImageVector,
    testTag: String,
    onClick: () -> Unit,
) {
    OutlinedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().testTag(testTag),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            androidx.compose.material3.Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(12.dp),
            ) {
                Box(modifier = Modifier.size(44.dp), contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(22.dp))
                }
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Text(description, color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall)
                Text(action, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge)
            }
            Icon(
                Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

@Composable
private fun ContractValueRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, modifier = Modifier.weight(1f))
        Text(value, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
internal fun FinancingAnnouncementsScreen() = FinancingContractDetailScreen(
    title = "公告栏",
    subtitle = "发布中的系统公告将在这里按 announcements.view 权限显示。",
    sections = listOf("公告列表", "公告详情", "备份结果提示"),
    testTag = "financing_announcements",
)

@Composable
internal fun FinancingPendingItemsScreen() = FinancingContractDetailScreen(
    title = "未处理事项",
    subtitle = "待补录、待办理和待支付事项将在接口接入后分类显示。",
    sections = listOf("待补充债务", "待打印单据", "待支付现金流"),
    testTag = "financing_pending_items",
)

@Composable
internal fun FinancingDailyDisbursementsScreen(date: LocalDate?) = FinancingContractDetailScreen(
    title = "每日融资到账",
    subtitle = date?.let { "统计日期：$it；计划与实际金额均以资金速记接口为准。" }
        ?: "日期参数无效，请返回融资总览后重试。",
    sections = listOf("计划放款", "实际放款", "关联项目与债务"),
    testTag = "financing_daily_disbursements",
)

@Composable
internal fun FinancingRepaymentMonthScreen(month: YearMonth?) = FinancingContractDetailScreen(
    title = "月份还款明细",
    subtitle = month?.let {
        "统计月份：${it.format(DateTimeFormatter.ofPattern("yyyy年MM月"))}；本金、利息及状态由现金流接口返回。"
    } ?: "月份参数无效，请返回融资总览后重试。",
    sections = listOf("本金合计", "利息合计", "每日还款事项"),
    testTag = "financing_repayment_month",
)

@Composable
internal fun FinancingProjectPipelineScreen(category: FinancingPipelineRoute?) = FinancingContractDetailScreen(
    title = "融资项目进度",
    subtitle = category?.let { "当前分类：${it.label}；项目范围和汇总以服务端返回为准。" }
        ?: "项目分类参数无效，请返回融资总览后重试。",
    sections = listOf("项目名称与主体", "计划融资与实际到账", "当前阶段与关键日期"),
    testTag = "financing_project_pipeline",
)

@Composable
private fun FinancingContractDetailScreen(
    title: String,
    subtitle: String,
    sections: List<String>,
    testTag: String,
) = PageContent {
    Column(modifier = Modifier.testTag(testTag), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(title, style = MaterialTheme.typography.headlineSmall)
        Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
        Text(
            "路由可用 · 正式数据接口尚未接入",
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            style = MaterialTheme.typography.titleSmall,
        )
    }
    sections.forEach { section ->
        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(section, style = MaterialTheme.typography.titleMedium)
                Text("暂无可展示的正式数据", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
    OutlinedButton(onClick = {}, enabled = false, modifier = Modifier.fillMaxWidth()) {
        Text("等待登录、权限与接口接入")
    }
}
