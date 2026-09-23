package com.citybond.mobile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.citybond.mobile.BuildConfig
import java.time.LocalDate
import java.time.YearMonth

private enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
) {
    HOME(AppRoutes.HOME, "首页", Icons.Outlined.Home),
    BUSINESS(AppRoutes.BUSINESS, "业务", Icons.Outlined.Menu),
    ASSISTANT(AppRoutes.ASSISTANT, "助手", Icons.Outlined.Create),
    TASKS(AppRoutes.TASKS, "任务", Icons.AutoMirrored.Outlined.List),
    PROFILE(AppRoutes.PROFILE, "我的", Icons.Outlined.Person),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityBondApp() {
    val navController = rememberNavController()
    val entry by navController.currentBackStackEntryAsState()
    val route = entry?.destination?.route
    val isTopLevel = route == null || route in AppRoutes.topLevelRoutes
    val selected = Destination.entries.firstOrNull { it.route == route }
        ?: if (route?.startsWith(AppRoutes.FINANCING_OVERVIEW) == true) Destination.BUSINESS else Destination.HOME
    val openTab: (Destination) -> Unit = { destination ->
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(AppRoutes.titleFor(route))
                        if (isTopLevel) {
                            Text(
                                "CITYBOND",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                },
                navigationIcon = {
                    if (!isTopLevel) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "返回")
                        }
                    }
                },
            )
        },
        bottomBar = {
            if (isTopLevel) {
                NavigationBar(containerColor = MaterialTheme.colorScheme.surfaceContainer) {
                    Destination.entries.forEach { destination ->
                        NavigationBarItem(
                            selected = selected == destination,
                            onClick = { openTab(destination) },
                            icon = { Icon(destination.icon, contentDescription = null) },
                            label = { Text(destination.label) },
                            modifier = Modifier.testTag("tab_${destination.route}"),
                        )
                    }
                }
            }
        },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = AppRoutes.HOME,
            modifier = Modifier.padding(padding),
        ) {
            composable(AppRoutes.HOME) {
                HomeScreen(openBusiness = { openTab(Destination.BUSINESS) })
            }
            composable(AppRoutes.BUSINESS) {
                BusinessCatalogScreen { moduleRoute -> navController.navigate(moduleRoute) }
            }
            composable(AppRoutes.FINANCING_OVERVIEW) {
                FinancingOverviewScreen(
                    openAnnouncements = { navController.navigate(AppRoutes.FINANCING_ANNOUNCEMENTS) },
                    openPendingItems = { navController.navigate(AppRoutes.FINANCING_PENDING_ITEMS) },
                    openDailyDisbursements = {
                        navController.navigate(AppRoutes.financingDailyDisbursements(it))
                    },
                    openRepaymentMonth = {
                        navController.navigate(AppRoutes.financingRepaymentMonth(it))
                    },
                    openProjectPipeline = {
                        navController.navigate(AppRoutes.financingProjectPipeline(it))
                    },
                )
            }
            composable(AppRoutes.FINANCING_ANNOUNCEMENTS) {
                FinancingAnnouncementsScreen()
            }
            composable(AppRoutes.FINANCING_PENDING_ITEMS) {
                FinancingPendingItemsScreen()
            }
            composable(
                route = AppRoutes.FINANCING_DAILY_DISBURSEMENTS,
                arguments = listOf(
                    navArgument(AppRoutes.FINANCING_DAILY_DATE_ARGUMENT) { type = NavType.StringType },
                ),
            ) { backStackEntry ->
                val date = backStackEntry.arguments
                    ?.getString(AppRoutes.FINANCING_DAILY_DATE_ARGUMENT)
                    ?.let { runCatching { LocalDate.parse(it) }.getOrNull() }
                FinancingDailyDisbursementsScreen(date)
            }
            composable(
                route = AppRoutes.FINANCING_REPAYMENT_MONTH,
                arguments = listOf(
                    navArgument(AppRoutes.FINANCING_REPAYMENT_MONTH_ARGUMENT) { type = NavType.StringType },
                ),
            ) { backStackEntry ->
                val month = backStackEntry.arguments
                    ?.getString(AppRoutes.FINANCING_REPAYMENT_MONTH_ARGUMENT)
                    ?.let { runCatching { YearMonth.parse(it) }.getOrNull() }
                FinancingRepaymentMonthScreen(month)
            }
            composable(
                route = AppRoutes.FINANCING_PROJECT_PIPELINE,
                arguments = listOf(
                    navArgument(AppRoutes.FINANCING_PIPELINE_ARGUMENT) { type = NavType.StringType },
                ),
            ) { backStackEntry ->
                val category = FinancingPipelineRoute.fromPath(
                    backStackEntry.arguments?.getString(AppRoutes.FINANCING_PIPELINE_ARGUMENT),
                )
                FinancingProjectPipelineScreen(category)
            }
            composable(AppRoutes.ASSISTANT) {
                PlaceholderPage(
                    title = "助手正在重新设计",
                    description = "对话功能将在隐私、工作流权限和业务草稿合同通过验收后接入。",
                    icon = Icons.Outlined.Create,
                )
            }
            composable(AppRoutes.TASKS) {
                PlaceholderPage(
                    title = "任务服务尚未接入",
                    description = "OCR、填表、导出等任务将按服务端真实生命周期统一展示。",
                    icon = Icons.AutoMirrored.Outlined.List,
                )
            }
            composable(AppRoutes.PROFILE) {
                ProfileScreen(
                    openConnection = {
                        navController.navigate(AppRoutes.CONNECTION) { launchSingleTop = true }
                    },
                )
            }
            if (BuildConfig.DEBUG) {
                composable(AppRoutes.CONNECTION) { ConnectionScreen() }
            }
        }
    }
}

@Composable
private fun HomeScreen(openBusiness: () -> Unit) = PageContent {
    Text("随时开启你的工作", style = MaterialTheme.typography.headlineMedium)
    Text(
        "项目、融资与协同，一个入口。",
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Icon(Icons.Outlined.Home, contentDescription = null, modifier = Modifier.size(36.dp))
            Text("你的移动工作台", style = MaterialTheme.typography.titleLarge)
            Text("基础框架已保留，业务功能将以服务端接口、权限和状态机为准重新实现。")
            Button(onClick = openBusiness) { Text("进入业务") }
        }
    }
    Text("快捷入口", style = MaterialTheme.typography.titleMedium)
    OutlinedCard(
        onClick = openBusiness,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Icon(Icons.Outlined.Menu, contentDescription = null)
            Column {
                Text("业务功能", style = MaterialTheme.typography.titleMedium)
                Text("查看当前重建状态", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
private fun ProfileScreen(openConnection: () -> Unit) = PageContent {
    Text("欢迎使用 CityBond", style = MaterialTheme.typography.headlineSmall)
    Text("账号和权限功能尚未接入。", color = MaterialTheme.colorScheme.onSurfaceVariant)
    if (BuildConfig.DEBUG) {
        OutlinedCard(
            onClick = openConnection,
            modifier = Modifier.fillMaxWidth().testTag("open_connection"),
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text("开发工具 · 连接检查", style = MaterialTheme.typography.titleMedium)
                Text("验证开发服务器连接", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
    Text("版本 ${BuildConfig.VERSION_NAME}", style = MaterialTheme.typography.labelMedium)
}

@Composable
private fun PlaceholderPage(
    title: String,
    description: String,
    icon: ImageVector,
) = PageContent {
    Icon(
        icon,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 48.dp).size(48.dp),
    )
    Text(title, style = MaterialTheme.typography.headlineSmall)
    Text(description, color = MaterialTheme.colorScheme.onSurfaceVariant)
}

@Composable
internal fun PageContent(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        content = content,
    )
}
