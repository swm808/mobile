package com.citybond.mobile.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.citybond.mobile.BuildConfig
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
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
    val assistantModel: AssistantViewModel = viewModel()
    val session by assistantModel.state.collectAsStateWithLifecycle()
    when {
        session.checkingSession -> AppSessionLoadingScreen()
        !session.authenticated -> AppLoginScreen(session, assistantModel)
        else -> AuthenticatedCityBondApp(assistantModel, session)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AuthenticatedCityBondApp(
    assistantModel: AssistantViewModel,
    session: AssistantUiState,
) {
    val navController = rememberNavController()
    var assistantSheetOpen by rememberSaveable { mutableStateOf(false) }
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
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface,
                ),
                title = {
                    Column {
                        Text(
                            AppRoutes.titleFor(route),
                            style = MaterialTheme.typography.titleLarge,
                        )
                        if (isTopLevel) {
                            Text(
                                "知城智融 · 智能体",
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
                Surface(color = MaterialTheme.colorScheme.surface, tonalElevation = 0.dp) {
                    Column {
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.surface,
                            tonalElevation = 0.dp,
                        ) {
                            Destination.entries.forEach { destination ->
                                NavigationBarItem(
                                    selected = selected == destination,
                                    onClick = { openTab(destination) },
                                    icon = { Icon(destination.icon, contentDescription = null) },
                                    label = { Text(destination.label) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.primary,
                                        selectedTextColor = MaterialTheme.colorScheme.primary,
                                        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    ),
                                    modifier = Modifier.testTag("tab_${destination.route}"),
                                )
                            }
                        }
                    }
                }
            }
        },
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            NavHost(
                navController = navController,
                startDestination = AppRoutes.HOME,
                modifier = Modifier.fillMaxSize(),
            ) {
            composable(AppRoutes.HOME) {
                HomeScreen(
                    accountName = session.accountName,
                    canUseAssistant = session.canUseAssistant,
                    openBusiness = { openTab(Destination.BUSINESS) },
                    openFinancing = { navController.navigate(AppRoutes.FINANCING_OVERVIEW) },
                    openLedger = { navController.navigate(AppRoutes.COMBINED_LEDGER) },
                    openAssistant = { openTab(Destination.ASSISTANT) },
                )
            }
            composable(AppRoutes.BUSINESS) {
                BusinessCatalogScreen { moduleRoute -> navController.navigate(moduleRoute) }
            }
            composable(AppRoutes.COMBINED_LEDGER) {
                CombinedLedgerScreen()
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
                    AssistantScreen(assistantModel)
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
                    accountName = session.accountName,
                    logout = assistantModel::logout,
                    openConnection = {
                        navController.navigate(AppRoutes.CONNECTION) { launchSingleTop = true }
                    },
                )
            }
                if (BuildConfig.DEBUG) {
                    composable(AppRoutes.CONNECTION) { ConnectionScreen() }
                }
            }
            if (route != null && route != AppRoutes.ASSISTANT) {
                FloatingActionButton(
                    onClick = { assistantSheetOpen = true },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(18.dp)
                        .testTag("assistant_fab"),
                ) {
                    Icon(Icons.Outlined.Create, contentDescription = "唤醒 AI 助手")
                }
            }
        }
    }
    if (assistantSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { assistantSheetOpen = false },
            containerColor = MaterialTheme.colorScheme.background,
        ) {
            AssistantQuickPanel(
                model = assistantModel,
                openFullAssistant = {
                    assistantSheetOpen = false
                    openTab(Destination.ASSISTANT)
                },
            )
        }
    }
}

@Composable
private fun HomeScreen(
    accountName: String,
    canUseAssistant: Boolean,
    openBusiness: () -> Unit,
    openFinancing: () -> Unit,
    openLedger: () -> Unit,
    openAssistant: () -> Unit,
) = PageContent {
    PageIntro(
        eyebrow = "移动工作台",
        title = "看清业务，快速行动",
        description = "你好，${accountName.ifBlank { "用户" }}。关键业务、数据入口和智能工具都已归位。",
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(listOf(Color(0xFF102A56), Color(0xFF175CA8), Color(0xFF087F72))),
                shape = RoundedCornerShape(24.dp),
            ),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Surface(color = Color.White.copy(alpha = 0.16f), shape = RoundedCornerShape(12.dp)) {
                    Icon(
                        Icons.Outlined.Home,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(10.dp).size(24.dp),
                    )
                }
                Surface(color = Color.White.copy(alpha = 0.15f), shape = RoundedCornerShape(999.dp)) {
                    Text(
                        "服务已连接",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            }
            Text("你的移动工作台", style = MaterialTheme.typography.headlineSmall, color = Color.White)
            Text(
                "从融资看板到综合台账，一次抵达；未接入的数据与功能会明确标示状态。",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.88f),
            )
            Button(
                onClick = openBusiness,
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF12315E),
                ),
            ) {
                Text("进入业务")
                Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null,
                    modifier = Modifier.padding(start = 8.dp).size(18.dp))
            }
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        MetricTile(
            value = "2",
            label = "已开放业务页面",
            modifier = Modifier.weight(1f),
        )
        MetricTile(
            value = "21",
            label = "业务能力总览",
            modifier = Modifier.weight(1f),
            tone = StatusTone.SUCCESS,
        )
    }

    SectionHeader(
        title = "常用功能",
        supportingText = "按工作场景整理，入口与状态一目了然",
        action = "全部业务",
        onAction = openBusiness,
    )
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        FeatureTile(
            title = "业务中心",
            description = "浏览全部业务能力",
            icon = Icons.Outlined.Menu,
            badge = "21 项",
            onClick = openBusiness,
            modifier = Modifier.weight(1f),
        )
        FeatureTile(
            title = "融资总览",
            description = "融资、余额与还款",
            icon = Icons.Outlined.Home,
            badge = "可进入",
            onClick = openFinancing,
            modifier = Modifier.weight(1f),
        )
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        FeatureTile(
            title = "综合台账",
            description = "项目与机构成本口径",
            icon = Icons.AutoMirrored.Outlined.List,
            badge = "可进入",
            onClick = openLedger,
            modifier = Modifier.weight(1f),
        )
        FeatureTile(
            title = "AI 助手",
            description = if (canUseAssistant) "继续会话与业务问答" else "当前账号暂无权限",
            icon = Icons.Outlined.Create,
            badge = if (canUseAssistant) "在线" else "受限",
            enabled = canUseAssistant,
            onClick = openAssistant,
            modifier = Modifier.weight(1f),
        )
    }
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            StatusPill("数据说明", StatusTone.PRIMARY)
            Text(
                "融资总览与综合台账已完成交互骨架，正式数据将在业务接口接入后展示。",
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
private fun ProfileScreen(
    accountName: String,
    logout: () -> Unit,
    openConnection: () -> Unit,
) = PageContent {
    PageIntro(
        eyebrow = "个人中心",
        title = "${accountName}，欢迎使用知城智融智能体",
        description = "管理当前会话，查看应用状态与开发连接。",
    )
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        shape = MaterialTheme.shapes.large,
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(18.dp)) {
                Text(
                    accountName.take(1).ifBlank { "C" },
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(accountName, style = MaterialTheme.typography.titleLarge)
                Text("账号已通过知城智融服务验证", style = MaterialTheme.typography.bodySmall)
            }
            StatusPill("已登录", StatusTone.SUCCESS)
        }
    }
    if (BuildConfig.DEBUG) {
        OutlinedCard(
            onClick = openConnection,
            modifier = Modifier.fillMaxWidth().testTag("open_connection"),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("开发工具 · 连接检查", style = MaterialTheme.typography.titleMedium)
                        Text(
                            "验证开发服务器地址与健康状态",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    Icon(
                        Icons.AutoMirrored.Outlined.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("应用版本", style = MaterialTheme.typography.bodyMedium)
            Text(BuildConfig.VERSION_NAME, style = MaterialTheme.typography.labelLarge)
        }
    }
    androidx.compose.material3.OutlinedButton(
        onClick = logout,
        modifier = Modifier.fillMaxWidth().testTag("logout"),
    ) {
        Text("安全退出登录")
    }
}

@Composable
private fun PlaceholderPage(
    title: String,
    description: String,
    icon: ImageVector,
) = PageContent {
    PageIntro(
        eyebrow = "任务中心",
        title = title,
        description = description,
    )
    Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = MaterialTheme.shapes.large) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(14.dp)) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(12.dp).size(26.dp),
                )
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("统一任务队列", style = MaterialTheme.typography.titleMedium)
                Text(
                    "接入后可集中查看执行中、待处理与失败任务",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            StatusPill("建设中")
        }
    }
    SectionHeader("规划能力", "任务类型与进度状态将在这里统一呈现")
    PlannedCapability("文档识别", "上传、OCR 分析与人工复核")
    PlannedCapability("自动填表", "模板匹配、字段校验与导出")
    PlannedCapability("报表导出", "后台生成、下载与失败重试")
}

@Composable
private fun PlannedCapability(title: String, description: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(color = MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(999.dp)) {
                Text("·", modifier = Modifier.padding(horizontal = 12.dp, vertical = 3.dp),
                    style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Text(description, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            StatusPill("待接入")
        }
    }
}

@Composable
internal fun PageContent(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 20.dp, top = 24.dp, end = 20.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        content = content,
    )
}
