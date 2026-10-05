package com.citybond.mobile.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

private enum class LedgerCostMetric(val label: String, val contractValue: String) {
    WEIGHTED("加权利率", "weighted_rate"),
    IRR365("IRR365", "irr365_rate"),
    XIRR("XIRR", "xirr_rate"),
}

private enum class LedgerDimension(val label: String) {
    PROJECT("按项目汇总"),
    INSTITUTION("按机构汇总"),
}

@Composable
internal fun CombinedLedgerScreen() = PageContent {
    var projectName by rememberSaveable { mutableStateOf("") }
    var creditorName by rememberSaveable { mutableStateOf("") }
    var debtorName by rememberSaveable { mutableStateOf("") }
    var operatorName by rememberSaveable { mutableStateOf("") }
    var costMetric by rememberSaveable { mutableStateOf(LedgerCostMetric.IRR365) }
    var dimension by rememberSaveable { mutableStateOf(LedgerDimension.PROJECT) }
    var includePrecontact by rememberSaveable { mutableStateOf(false) }
    var submittedFilterCount by rememberSaveable { mutableIntStateOf(-1) }

    Column(
        modifier = Modifier.testTag("combined_ledger"),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text("DATA CENTER", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
        Text("综合台账", style = MaterialTheme.typography.headlineSmall)
        Text(
            "统一查看项目金额折算与综合成本情况",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }

    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp),
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.10f),
                shape = RoundedCornerShape(999.dp),
            ) {
                Text(
                    "最新合同",
                    modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Text("已对齐 develop 业务口径", style = MaterialTheme.typography.titleMedium)
            Text(
                "页面筛选、汇总维度与字段口径已就绪；登录和 Cookie 会话接入前不请求正式财务数据。",
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.82f),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }

    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = androidx.compose.material3.CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("查询条件", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
            LedgerFilterField("项目名称或编号", projectName) { projectName = it }
            LedgerFilterField("债权机构", creditorName) { creditorName = it }
            LedgerFilterField("债务单位", debtorName) { debtorName = it }
            LedgerFilterField("经办人", operatorName) { operatorName = it }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedButton(
                    onClick = {
                        projectName = ""
                        creditorName = ""
                        debtorName = ""
                        operatorName = ""
                        includePrecontact = false
                        submittedFilterCount = -1
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Text("重置")
                }
                Button(
                    onClick = {
                        submittedFilterCount = listOf(projectName, creditorName, debtorName, operatorName)
                            .count { it.isNotBlank() }
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Text("查询")
                }
            }
            if (submittedFilterCount >= 0) {
                Text(
                    "已暂存 $submittedFilterCount 项条件，等待登录会话接入后查询。",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }

    Text("综合成本计算方式", style = MaterialTheme.typography.titleMedium)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        LedgerCostMetric.entries.forEach { metric ->
            FilterChip(
                selected = costMetric == metric,
                onClick = { costMetric = metric },
                label = { Text(metric.label) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    }
    Text(
        "当前参数：${costMetric.contractValue}。算法不同会影响已付金额和可付金额折算。",
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.bodySmall,
    )

    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = androidx.compose.material3.CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("汇总维度", style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                LedgerDimension.entries.forEach { item ->
                    FilterChip(
                        selected = dimension == item,
                        onClick = { dimension = item },
                        label = { Text(item.label) },
                        shape = RoundedCornerShape(10.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.primary,
                        ),
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = includePrecontact,
                    onCheckedChange = { includePrecontact = it },
                )
                Text("显示前期对接项目")
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            LedgerEmptyState(dimension)
        }
    }

    Text("返回字段", style = MaterialTheme.typography.titleMedium)
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = androidx.compose.material3.CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            LedgerContractRow("项目口径", "项目数 · 测算失败数")
            LedgerContractRow("成本口径", "成本上限 · 当前综合成本")
            LedgerContractRow("折算口径", "已付金额 · 可付金额 · 折算时间")
            LedgerContractRow("机构口径", "放款额 · 债务人 · 分支机构")
        }
    }

    OutlinedButton(onClick = {}, enabled = false, modifier = Modifier.fillMaxWidth()) {
        Text("导出需 project.export 权限")
    }
}

@Composable
private fun LedgerFilterField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun LedgerEmptyState(dimension: LedgerDimension) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
        Text(
            if (dimension == LedgerDimension.PROJECT) {
                "暂无符合条件的综合台账项目"
            } else {
                "暂无符合条件的机构汇总"
            },
            style = MaterialTheme.typography.titleSmall,
        )
        Text(
            "正式数据来自 GET /api/v1/combined-ledger。",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall,
        )
        }
    }
}

@Composable
private fun LedgerContractRow(label: String, value: String) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(label, modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelLarge)
            Text(value, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
        }
    }
}
