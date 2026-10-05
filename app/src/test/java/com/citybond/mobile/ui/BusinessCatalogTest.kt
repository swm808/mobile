package com.citybond.mobile.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BusinessCatalogTest {
    @Test
    fun catalogMatchesPinnedDevelopPermissionGroups() {
        val expectedGroups = setOf(
            "ai_report_generation", "ai_settings", "announcements", "asset", "assistant",
            "backup", "cost_estimation", "credit", "debt", "debt_init", "development", "documents",
            "engineering", "extraction", "finance", "group_scope_management", "guarantee", "holidays",
            "logger", "lpr", "master_data", "materials", "memo", "ocr", "ocr_settings", "overview",
            "parameters", "pending_debt", "permissions", "project", "query", "sheet_fill", "sheet_rules",
            "users",
        )

        assertEquals("390218ac3a1c13dbd0a3cf42b19603a3d130cf64", DevelopBusinessCatalog.BASELINE)
        assertEquals(21, DevelopBusinessCatalog.modules.size)
        assertEquals(expectedGroups, DevelopBusinessCatalog.featureGroups)
        assertEquals(DevelopBusinessCatalog.modules.size, DevelopBusinessCatalog.modules.map { it.key }.toSet().size)
    }

    @Test
    fun visibilityRequiresAtLeastOneExplicitViewPermission() {
        assertTrue(DevelopBusinessCatalog.visibleModules(emptySet()).isEmpty())
        val visible = DevelopBusinessCatalog.visibleModules(setOf("debt.view", "users.edit"))
        assertEquals(listOf("debts", "change-management"), visible.map { it.key })
        assertFalse(visible.first().isVisibleTo(setOf("debt.edit")))
        assertEquals(
            listOf("group-scope-statistics"),
            DevelopBusinessCatalog.visibleModules(setOf("asset.group_manage")).map { it.key },
        )
    }

    @Test
    fun deliveryStatesMatchLatestDevelop() {
        val states = DevelopBusinessCatalog.modules.associate { it.key to it.state }
        assertEquals(ModuleDeliveryState.UI_READY, states["supplementary-debt-fees"])
        assertEquals(ModuleDeliveryState.UPGRADING, states["ai-report-generation"])
        assertEquals(ModuleDeliveryState.PRIVACY_GATE, states["assistant"])
    }
}
