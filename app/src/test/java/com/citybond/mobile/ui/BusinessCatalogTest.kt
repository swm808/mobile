package com.citybond.mobile.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BusinessCatalogTest {
    @Test
    fun catalogMatchesPinnedDevelopPermissionGroups() {
        val expectedGroups = setOf(
            "ai_report_generation", "ai_settings", "announcements", "asset", "assistant", "audit",
            "backup", "cost_estimation", "credit", "debt", "debt_init", "development", "documents",
            "engineering", "extraction", "finance", "guarantee", "holidays", "lpr", "master_data",
            "materials", "memo", "ocr", "ocr_settings", "overview", "parameters", "pending_debt",
            "permissions", "project", "query", "sheet_fill", "sheet_rules", "users",
        )

        assertEquals("a7da695f46a542ebb5a3bfcab87f4238df89f2fa", DevelopBusinessCatalog.BASELINE)
        assertEquals(3, DevelopBusinessCatalog.PERMISSION_CATALOG_VERSION)
        assertEquals(16, DevelopBusinessCatalog.modules.size)
        assertEquals(expectedGroups, DevelopBusinessCatalog.featureGroups)
        assertEquals(DevelopBusinessCatalog.modules.size, DevelopBusinessCatalog.modules.map { it.key }.toSet().size)
    }

    @Test
    fun visibilityRequiresAtLeastOneExplicitViewPermission() {
        assertTrue(DevelopBusinessCatalog.visibleModules(emptySet()).isEmpty())
        val visible = DevelopBusinessCatalog.visibleModules(setOf("debt.view", "users.edit"))
        assertEquals(listOf("debts"), visible.map { it.key })
        assertFalse(visible.single().isVisibleTo(setOf("debt.edit")))
    }

    @Test
    fun placeholdersAndAssistantRemainNonDeliverable() {
        val states = DevelopBusinessCatalog.modules.associate { it.key to it.state }
        assertEquals(ModuleDeliveryState.UPGRADING, states["cost-estimation"])
        assertEquals(ModuleDeliveryState.UPGRADING, states["ai-report-generation"])
        assertEquals(ModuleDeliveryState.PRIVACY_GATE, states["assistant"])
    }
}
