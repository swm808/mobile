package com.citybond.mobile.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "融资总览 · 浅色",
    group = "主要页面",
    device = Devices.PIXEL_4,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "融资总览 · 深色",
    group = "主要页面",
    device = Devices.PIXEL_4,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun FinancingOverviewPreview() = PreviewSurface {
    FinancingOverviewScreen(
        openAnnouncements = {},
        openPendingItems = {},
        openDailyDisbursements = {},
        openRepaymentMonth = {},
        openProjectPipeline = {},
    )
}

@Preview(
    name = "业务目录",
    group = "主要页面",
    device = Devices.PIXEL_4,
    showSystemUi = true,
)
@Composable
private fun BusinessCatalogPreview() = PreviewSurface {
    BusinessCatalogScreen(openModule = {})
}

@Preview(
    name = "综合台账",
    group = "主要页面",
    device = Devices.PIXEL_4,
    showSystemUi = true,
    
)
@Composable
private fun CombinedLedgerPreview() = PreviewSurface {
    CombinedLedgerScreen()
}

@Composable
private fun PreviewSurface(content: @Composable () -> Unit) {
    CityBondTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            content()
        }
    }
}
