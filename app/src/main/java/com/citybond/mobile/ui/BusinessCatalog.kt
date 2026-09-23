package com.citybond.mobile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

internal enum class ContractSection(val label: String) {
    FINANCING("项目与融资"),
    SKILL("技能 / Skill"),
    ADMIN("管理员功能"),
    OTHER("其他设置"),
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
) {
    val viewPermission: String = "$group.view"
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
    const val BASELINE = "a7da695f46a542ebb5a3bfcab87f4238df89f2fa"
    const val PERMISSION_CATALOG_VERSION = 3

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
        module("project-ledger", "项目台账", ContractSection.FINANCING, "项目、融资、速记和成本审批的统一入口", "project" to "项目台账管理"),
        module("debts", "融资明细", ContractSection.FINANCING, "正式债务与待补充债务", "debt" to "正式债务", "pending_debt" to "待补充债务"),
        module("debt-memo", "资金速记", ContractSection.FINANCING, "计划、实际、转换、关联与资金缺口", "memo" to "资金速记管理"),
        module("dataentry", "跨部门协同", ContractSection.FINANCING, "财务、工程和资产协同", "finance" to "财务部", "engineering" to "工程部", "asset" to "资产部"),
        module("ocr-upload", "AI 录入", ContractSection.SKILL, "文档上传、识别、复核与历史", "ocr" to "文档上传、识别与历史"),
        module("ocr-sheet-fill", "自动填表", ContractSection.SKILL, "自动填表、任务历史与债务初始化", "sheet_fill" to "自动填表与填表历史", "debt_init" to "数据初始化"),
        module("cost-estimation", "成本测算", ContractSection.SKILL, "服务端当前仅提供升级中菜单权限", "cost_estimation" to "菜单入口（升级中）", state = ModuleDeliveryState.UPGRADING),
        module("query", "综合查询", ContractSection.SKILL, "自然语言、高级筛选、报表与导出", "query" to "综合查询"),
        module("documents", "业务审批", ContractSection.SKILL, "还本付息单、费用申请单与成本审批单", "documents" to "业务审批"),
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
            "audit" to "审计日志",
            "development" to "开发测试数据",
        ),
        module("guarantee", "对外担保和授信统计", ContractSection.ADMIN, "对外担保台账、OCR 预填与授信统计", "guarantee" to "对外担保", "credit" to "授信统计"),
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
    Text("develop 业务合同已同步", style = MaterialTheme.typography.headlineSmall)
    Text(
        "基线 ${DevelopBusinessCatalog.BASELINE.take(8)} · 权限目录 v${DevelopBusinessCatalog.PERMISSION_CATALOG_VERSION}",
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.labelLarge,
    )
    Text(
        "这里展示后端业务覆盖清单，不代表功能已经接入。完成登录和权限会话后，入口将按 /auth/me 返回的权限显示。",
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    ContractSection.entries.forEach { section ->
        val modules = DevelopBusinessCatalog.modules.filter { it.section == section }
        Text("${section.label} · ${modules.size}", style = MaterialTheme.typography.titleMedium)
        modules.forEach { module -> BusinessModuleCard(module, openModule) }
    }
}

@Composable
private fun BusinessModuleCard(
    module: BusinessModuleContract,
    openModule: (String) -> Unit,
) {
    Card(
        onClick = { module.route?.let(openModule) },
        enabled = module.route != null,
        modifier = Modifier.fillMaxWidth().testTag("contract_module_${module.key}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(module.label, style = MaterialTheme.typography.titleMedium)
            Text(
                module.state.label,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium,
            )
            Text(module.description, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(
                module.features.joinToString(" · ") { it.label },
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}
