package com.citybond.mobile.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.citybond.mobile.model.DebtStatus
import com.citybond.mobile.model.FinancingKind

internal val FinancingKind.displayName get() = when (this) {
    FinancingKind.LOAN -> "银行贷款"
    FinancingKind.BOND -> "债券融资"
    FinancingKind.BILL -> "票据/费用"
}

internal val DebtStatus.displayName get() = when (this) {
    DebtStatus.INCOMPLETE -> "待完善"
    DebtStatus.ACTIVE -> "未结清"
    DebtStatus.SETTLED -> "已结清"
}

@Composable fun DebtStatusBadge(status: DebtStatus) {
    val colors = when (status) {
        DebtStatus.INCOMPLETE -> Color(0xFFFFF1C7) to Color(0xFF735C00)
        DebtStatus.ACTIVE -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.onPrimaryContainer
        DebtStatus.SETTLED -> Color(0xFFDCEFE1) to Color(0xFF245F38)
    }
    Surface(color = colors.first, contentColor = colors.second, shape = RoundedCornerShape(999.dp)) {
        Text(status.displayName, Modifier.padding(horizontal = 10.dp, vertical = 5.dp), style = MaterialTheme.typography.labelMedium)
    }
}

@Composable fun DebtRecordCard(record: DebtUiRecord, onClick: () -> Unit) {
    OutlinedCard(onClick, Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Column(Modifier.weight(1f)) {
                    Text(record.projectName, style = MaterialTheme.typography.titleMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    Text("${record.number} · ${record.debtType} · ${record.debt.kind.displayName}", style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                DebtStatusBadge(record.debt.status)
            }
            Text("${record.debtorNames.joinToString("、")} → ${record.creditorNames.joinToString("、").ifBlank { "债权人待完善" }}",
                maxLines = 1, overflow = TextOverflow.Ellipsis)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("放款 ${record.disbursementAmount.yuan.asCurrency()}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("余额 ${record.debt.outstandingPrincipal.yuan.asCurrency()}", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
