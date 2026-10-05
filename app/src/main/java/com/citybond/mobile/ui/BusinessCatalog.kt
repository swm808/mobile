package com.citybond.mobile.ui

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

internal enum class ContractSection(val label: String, val icon: ImageVector) {
    FINANCING("项目与融资", Icons.Outlined.Home),
    SKILL("智能工具", Icons.Outlined.Create),
    ADMIN("管理配置", Icons.Outlined.Person),
    OTHER("其他设置", Icons.Outlined.Menu),
}

internal enum class ModuleDeliveryState(val label: String) {
    PENDING("待正式接口接入"),
    UI_READY("UI 与路由已完成 · 待正式接口接入"),
    UPGRADING("后端升级中"),
    PRIVACY_GATE("等待隐私门禁"),
}

internal data class ContractFeature(
    val group: String,
    val label: String,
    val permissionCode: String = "$group.view",
) {
    val viewPermission: String get() = permissionCode
}

internal data class BusinessModuleContract(
    val key: String,
    val label: String,
    val section: ContractSection,
    val description: String,
    val features: List<ContractFeature>,
    val state: ModuleDeliveryState = ModuleDeliveryState.PENDING,
    val route: String? = null,
) {
    fun isVisibleTo(grantedPermissions: Set<String>): Boolean =
        features.any { it.viewPermission in grantedPermissions }
}

/**
 * Mirrors permission_catalog.py at the pinned develop commit. These are contract
 * records, not enabled screens; visibility must use /auth/me permissions once
 * the R01/R02 session foundation is connected.
 */
internal object DevelopBusinessCatalog {
    const val BASELINE = "390218ac3a1c13dbd0a3cf42b19603a3d130cf64"

    val modules = listOf(
        module(
            "financing-overview",
            "融资总览",
            ContractSection.FINANCING,
            "总览指标、配置与公告栏",
            "overview" to "总览指标与配置",
            "announcements" to "公告栏",
            state = ModuleDeliveryState.UI_READY,
            route = AppRoutes.FINANCING_OVERVIEW,
        ),
        module("project-ledger", "项目台账", ContractSection.FINANCING, "项目、融资与成本审批的统一入口", "project" to "项目台账管理"),
        module("debt-memo", "资金速记", ContractSection.FINANCING, "计划、实际、转换、关联与资金缺口", "memo" to "资金速记管理"),
        module("debts", "融资明细", ContractSection.FINANCING, "正式债务与待补充债务", "debt" to "正式债务", "pending_debt" to "待补充债务"),
        module(
            "supplementary-debt-fees",
            "附属债务/费用",
            ContractSection.FINANCING,
            "综合台账与中收、派生测算",
            "project" to "综合台账",
            "cost_estimation" to "中收和派生测算",
            state = ModuleDeliveryState.UI_READY,
            route = AppRoutes.COMBINED_LEDGER,
        ),
        module("guarantee", "担保明细", ContractSection.FINANCING, "对外担保申请、保后管理及明细统计", "guarantee" to "担保业务"),
        module("change-management", "变动管理", ContractSection.FINANCING, "LPR 自动调整、人工调整和人员变动", "debt" to "债务变动管理"),
        module("dataentry", "跨部门协同", ContractSection.FINANCING, "财务、工程和资产协同", "finance" to "财务部", "engineering" to "工程部", "asset" to "资产部"),
        module("ocr-upload", "AI 录入", ContractSection.SKILL, "文档上传、识别、复核与历史", "ocr" to "文档上传、识别与历史"),
        module("daily-statistics", "日常统计表", ContractSection.SKILL, "按项目、债务、放款和费用统计日常业务", "query" to "日常统计"),
        module("ocr-sheet-fill", "自动填表", ContractSection.SKILL, "自动填表、任务历史与债务初始化", "sheet_fill" to "自动填表与填表历史", "debt_init" to "数据初始化"),
        module("query", "综合查询", ContractSection.SKILL, "自然语言、高级筛选、报表与导出", "query" to "综合查询"),
        module("documents", "支付中心", ContractSection.SKILL, "还本付息、业务申请、回单核验与办理", "documents" to "支付中心"),
        module("cost-estimation", "成本测算", ContractSection.SKILL, "中收、派生与资金占用综合成本测算", "cost_estimation" to "成本测算"),
        module("valueadded", "基础材料 AI 打包", ContractSection.SKILL, "材料分析、关联、打包与下载", "materials" to "材料分析与打包"),
        module("ai-report-generation", "AI 报告生成（定制）", ContractSection.SKILL, "服务端当前仅提供升级中菜单权限", "ai_report_generation" to "菜单入口（升级中）", state = ModuleDeliveryState.UPGRADING),
        module("master-data", "债务信息维护", ContractSection.ADMIN, "债务单位、债权机构、融资产品等基础资料", "master_data" to "基础资料"),
        module(
            "system",
            "系统配置",
            ContractSection.ADMIN,
            "按独立权限开放系统管理能力",
            "ocr_settings" to "OCR 流程",
            "ai_settings" to "AI 调度",
            "backup" to "备份与恢复",
            "extraction" to "合同提取",
            "sheet_rules" to "台账字段与规则",
            "parameters" to "系统参数",
            "users" to "用户账号",
            "permissions" to "权限管理",
            "holidays" to "节假日",
            "lpr" to "LPR 利率",
            "logger" to "日志中心",
            "development" to "开发测试数据",
        ),
        BusinessModuleContract(
            key = "group-scope-statistics",
            label = "集团范围与统计口径",
            section = ContractSection.ADMIN,
            description = "维护集团范围并统一资产统计口径",
            features = listOf(ContractFeature("group_scope_management", "集团范围与统计口径", "asset.group_manage")),
        ),
        module("credit-statistics", "授信统计", ContractSection.ADMIN, "债务授信汇总与银行映射", "credit" to "授信统计"),
        module("assistant", "AI 对话", ContractSection.OTHER, "本地会话、多轮续办与业务草稿", "assistant" to "AI 对话", state = ModuleDeliveryState.PRIVACY_GATE),
    )

    val featureGroups: Set<String> = modules.flatMap { module ->
        module.features.map(ContractFeature::group)
    }.toSet()

    fun visibleModules(grantedPermissions: Set<String>): List<BusinessModuleContract> =
        modules.filter { it.isVisibleTo(grantedPermissions) }

    private fun module(
        key: String,
        label: String,
        section: ContractSection,
        description: String,
        vararg features: Pair<String, String>,
        state: ModuleDeliveryState = ModuleDeliveryState.PENDING,
        route: String? = null,
    ) = BusinessModuleContract(
        key = key,
        label = label,
        section = section,
        description = description,
        features = features.map { (group, featureLabel) -> ContractFeature(group, featureLabel) },
        state = state,
        route = route,
    )
}

@Composable
internal fun BusinessCatalogScreen(openModule: (String) -> Unit) = PageContent {
    PageIntro(
        eyebrow = "业务中心",
        title = "最新 develop 功能已同步",
        description = "按工作领域浏览所有能力；可用入口、权限状态和建设进度均清晰标识。",
    )
    val available = DevelopBusinessCatalog.modules.filter { it.route != null }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        MetricTile("${available.size}", "已开放页面", Modifier.weight(1f))
        MetricTile(
            "${DevelopBusinessCatalog.modules.size}",
            "业务能力总览",
            Modifier.weight(1f),
            StatusTone.SUCCESS,
        )
    }
    Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = MaterialTheme.shapes.medium) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StatusPill("状态说明", StatusTone.PRIMARY)
            Text(
                "彩色入口可直接使用；灰色入口展示完整能力范围与当前建设状态。",
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
    SectionHeader("快捷进入", "已完成移动端交互与路由的业务页面")
    available.forEach { module -> BusinessModuleCard(module, openModule) }
    SectionHeader("建设路线图", "全部业务领域按类别展开，无需逐层查找")
    ContractSection.entries.forEach { section ->
        val modules = DevelopBusinessCatalog.modules.filter { it.section == section && it.route == null }
        if (modules.isNotEmpty()) {
            BusinessSectionHeader(section, modules.size)
            modules.forEach { module -> BusinessModuleCard(module, openModule) }
        }
    }
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceContainer,
        shape = MaterialTheme.shapes.medium,
    ) {
        Text(
            "服务端基线 ${DevelopBusinessCatalog.BASELINE.take(8)} · 未开放模块仅展示建设状态。",
            modifier = Modifier.padding(14.dp),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun BusinessSectionHeader(section: ContractSection, count: Int) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceContainer,
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(11.dp),
            ) {
                Icon(
                    section.icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(9.dp).size(20.dp),
                )
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(section.label, style = MaterialTheme.typography.titleMedium)
                Text(
                    "$count 个模块 · 能力范围已梳理",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            StatusPill("待接入")
        }
    }
}

@Composable
private fun BusinessModuleCard(
    module: BusinessModuleContract,
    openModule: (String) -> Unit,
) {
    OutlinedCard(
        onClick = { module.route?.let(openModule) },
        enabled = module.route != null,
        modifier = Modifier.fillMaxWidth().testTag("contract_module_${module.key}"),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Surface(
                color = if (module.route != null) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surfaceContainerHigh
                },
                shape = RoundedCornerShape(12.dp),
            ) {
                Box(modifier = Modifier.size(42.dp), contentAlignment = Alignment.Center) {
                    Icon(
                        module.section.icon,
                        contentDescription = null,
                        tint = if (module.route != null) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(21.dp),
                    )
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(7.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(module.label, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                    ModuleStatePill(module.state)
                }
                Text(
                    module.description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        module.features.joinToString(" · ") { it.label },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 7.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            if (module.route != null) {
                androidx.compose.material3.Icon(
                    Icons.AutoMirrored.Outlined.ArrowForward,
                    contentDescription = "进入${module.label}",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 2.dp).size(20.dp),
                )
            }
        }
    }
}

@Composable
private fun ModuleStatePill(state: ModuleDeliveryState) {
    val tone = if (state == ModuleDeliveryState.UI_READY) StatusTone.SUCCESS else StatusTone.NEUTRAL
    val label = when (state) {
        ModuleDeliveryState.UI_READY -> "可进入"
        ModuleDeliveryState.UPGRADING -> "升级中"
        ModuleDeliveryState.PRIVACY_GATE -> "待授权"
        ModuleDeliveryState.PENDING -> "待接入"
    }
    StatusPill(label, tone)
}
