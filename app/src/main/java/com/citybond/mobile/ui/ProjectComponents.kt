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
import com.citybond.mobile.model.ProjectStage
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

internal val ProjectStage.displayName get() = when (this) {
    ProjectStage.NEGOTIATING -> "接洽中"
    ProjectStage.FINANCING -> "融资中"
    ProjectStage.COMPLETED -> "已完成"
    ProjectStage.ABANDONED -> "已放弃"
}

@Composable
fun ProjectStageBadge(stage: ProjectStage) {
    val colors = when (stage) {
        ProjectStage.NEGOTIATING -> Color(0xFFFFF1C7) to Color(0xFF735C00)
        ProjectStage.FINANCING -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.onPrimaryContainer
        ProjectStage.COMPLETED -> Color(0xFFDCEFE1) to Color(0xFF245F38)
        ProjectStage.ABANDONED -> MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant
    }
    Surface(color = colors.first, contentColor = colors.second, shape = RoundedCornerShape(999.dp)) {
        Text(stage.displayName, Modifier.padding(horizontal = 10.dp, vertical = 5.dp), style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
fun ProjectRecordCard(record: ProjectUiRecord, onClick: () -> Unit) {
    OutlinedCard(onClick, Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Column(Modifier.weight(1f)) {
                    Text(record.project.name, style = MaterialTheme.typography.titleMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    Text(record.code, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                ProjectStageBadge(record.project.stage)
            }
            Text(record.debtorName, style = MaterialTheme.typography.bodyMedium)
            Text(record.creditors.joinToString("、"), style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun ProjectSection(title: String, action: (@Composable () -> Unit)? = null, content: @Composable ColumnScope.() -> Unit) {
    OutlinedCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f)); action?.invoke()
            }
            HorizontalDivider(); content()
        }
    }
}

@Composable
fun ProjectInfoRow(label: String, value: String, warning: Boolean = false) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(label, Modifier.width(104.dp), color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, Modifier.weight(1f), color = if (warning) MaterialTheme.colorScheme.error else LocalContentColor.current)
    }
}

internal fun BigDecimal.asCurrency(): String = NumberFormat.getCurrencyInstance(Locale.CHINA).format(this)
internal fun BigDecimal?.asRate(): String = this?.stripTrailingZeros()?.toPlainString()?.plus("%") ?: "—"
