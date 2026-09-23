package com.citybond.mobile.ui

import java.time.LocalDate
import java.time.YearMonth
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AppRoutesTest {
    @Test
    fun financingRouteBuildersUseContractSafeValues() {
        assertEquals(
            "financing-overview/daily-disbursements/2026-09-11",
            AppRoutes.financingDailyDisbursements(LocalDate.parse("2026-09-11")),
        )
        assertEquals(
            "financing-overview/repayments/2026-09",
            AppRoutes.financingRepaymentMonth(YearMonth.parse("2026-09")),
        )
        assertEquals(
            "financing-overview/projects/approval-pending",
            AppRoutes.financingProjectPipeline(FinancingPipelineRoute.APPROVAL_PENDING),
        )
    }

    @Test
    fun routeMetadataKeepsFinancingPagesNestedUnderBusiness() {
        assertTrue(AppRoutes.FINANCING_OVERVIEW !in AppRoutes.topLevelRoutes)
        assertEquals("融资总览", AppRoutes.titleFor(AppRoutes.FINANCING_OVERVIEW))
        assertEquals("月份还款明细", AppRoutes.titleFor(AppRoutes.FINANCING_REPAYMENT_MONTH))
        assertEquals(
            FinancingPipelineRoute.DISBURSEMENT_PENDING,
            FinancingPipelineRoute.fromPath("disbursement-pending"),
        )
    }
}
