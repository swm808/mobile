package com.citybond.mobile.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.citybond.mobile.BuildConfig

private enum class Destination(val route: String, val label: String, val icon: ImageVector) {
    HOME(AppRoutes.HOME, "首页", Icons.Outlined.Home), BUSINESS(AppRoutes.BUSINESS, "业务", Icons.Outlined.Menu),
    ASSISTANT(AppRoutes.ASSISTANT, "助手", Icons.Outlined.Create), TASKS(AppRoutes.TASKS, "任务", Icons.AutoMirrored.Outlined.List),
    PROFILE(AppRoutes.PROFILE, "我的", Icons.Outlined.Person),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun CityBondApp() {
    val navController = rememberNavController()
    val projects = remember { demoProjectRecords() }
    val debts = remember { demoDebtRecords() }
    val route = navController.currentBackStackEntryAsState().value?.destination?.route
    val isRoot = Destination.entries.any { it.route == route }
    val selected = Destination.entries.firstOrNull { it.route == route } ?: Destination.BUSINESS
    Scaffold(
        topBar = { TopAppBar(title = { Text(routeTitle(route)) }, navigationIcon = {
            if (!isRoot) IconButton({ navController.popBackStack() }) { Icon(Icons.AutoMirrored.Outlined.ArrowBack, "返回") }
        }) },
        bottomBar = { if (isRoot) AppBottomBar(selected) { navController.openTab(it) } },
    ) { padding -> AppNavHost(navController, projects, debts, Modifier.padding(padding)) }
}

private fun routeTitle(route: String?) = when (route) {
    AppRoutes.HOME -> "首页"; AppRoutes.BUSINESS -> "业务"; AppRoutes.ASSISTANT -> "助手"
    AppRoutes.TASKS -> "任务"; AppRoutes.PROFILE -> "我的"; AppRoutes.CONNECTION -> "连接检查"
    AppRoutes.PROJECTS -> "项目台账"; AppRoutes.PROJECT_CREATE -> "新建项目"
    AppRoutes.PROJECT_DETAIL -> "项目详情"; AppRoutes.PROJECT_EDIT -> "编辑项目"
    AppRoutes.DEBTS -> "债务台账"; AppRoutes.DEBT_CREATE -> "新增债务"; AppRoutes.DEBT_INCOMPLETE -> "待完善债务"
    AppRoutes.DEBT_DETAIL -> "债务详情"; AppRoutes.DEBT_EDIT -> "编辑债务"; else -> "CityBond"
}

@Composable private fun AppBottomBar(selected: Destination, open: (Destination) -> Unit) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surfaceContainer) {
        Destination.entries.forEach { item -> NavigationBarItem(selected == item, { open(item) },
            { Icon(item.icon, null) }, label = { Text(item.label) }, modifier = Modifier.testTag("tab_${item.route}")) }
    }
}

private fun NavHostController.openTab(destination: Destination) = navigate(destination.route) {
    popUpTo(graph.findStartDestination().id) { saveState = true }; launchSingleTop = true; restoreState = true
}

@Composable private fun AppNavHost(nav: NavHostController, projects: MutableList<ProjectUiRecord>, debts: MutableList<DebtUiRecord>, modifier: Modifier) {
    NavHost(nav, AppRoutes.HOME, modifier) {
        composable(AppRoutes.HOME) { HomeScreen { nav.openTab(Destination.BUSINESS) } }
        composable(AppRoutes.BUSINESS) { BusinessScreen({ nav.navigate(AppRoutes.PROJECTS) }, { nav.navigate(AppRoutes.DEBTS) }) }
        composable(AppRoutes.PROJECTS) { ProjectListScreen(projects, { nav.navigate(AppRoutes.projectDetail(it)) },
            { nav.navigate(AppRoutes.PROJECT_CREATE) }) }
        composable(AppRoutes.PROJECT_CREATE) { ProjectFormScreen(null) { project ->
            projects.add(0, project); nav.navigate(AppRoutes.projectDetail(project.project.id)) { popUpTo(AppRoutes.PROJECTS) }
        } }
        composable(AppRoutes.PROJECT_DETAIL) { entry ->
            val id = entry.arguments?.getString("projectId")
            ProjectDetailScreen(projects.find { it.project.id == id }) { nav.navigate(AppRoutes.projectEdit(it)) }
        }
        composable(AppRoutes.PROJECT_EDIT) { entry ->
            val id = entry.arguments?.getString("projectId")
            ProjectFormScreen(projects.find { it.project.id == id }) { updated ->
                projects.indexOfFirst { it.project.id == updated.project.id }.takeIf { it >= 0 }?.let { projects[it] = updated }
                nav.popBackStack()
            }
        }
        composable(AppRoutes.DEBTS) { DebtListScreen(debts, { nav.navigate(AppRoutes.debtDetail(it)) },
            { nav.navigate(AppRoutes.DEBT_CREATE) }, { nav.navigate(AppRoutes.DEBT_INCOMPLETE) }) }
        composable(AppRoutes.DEBT_CREATE) { DebtFormScreen(null) { debt ->
            debts.add(0, debt); nav.navigate(AppRoutes.debtDetail(debt.debt.id)) { popUpTo(AppRoutes.DEBTS) }
        } }
        composable(AppRoutes.DEBT_INCOMPLETE) { IncompleteDebtScreen(debts) { nav.navigate(AppRoutes.debtEdit(it)) } }
        composable(AppRoutes.DEBT_DETAIL) { entry ->
            val id = entry.arguments?.getString("debtId")
            DebtDetailScreen(debts.find { it.debt.id == id }) { nav.navigate(AppRoutes.debtEdit(it)) }
        }
        composable(AppRoutes.DEBT_EDIT) { entry ->
            val id = entry.arguments?.getString("debtId")
            DebtFormScreen(debts.find { it.debt.id == id }) { updated ->
                debts.indexOfFirst { it.debt.id == updated.debt.id }.takeIf { it >= 0 }?.let { debts[it] = updated }
                nav.popBackStack()
            }
        }
        composable(AppRoutes.ASSISTANT) { PlaceholderPage("助手尚未接入", "对话功能将在独立批次开发。", Icons.Outlined.Create) }
        composable(AppRoutes.TASKS) { PlaceholderPage("任务服务尚未接入", "后续可在这里跟踪上传、识别与导出进度。", Icons.AutoMirrored.Outlined.List) }
        composable(AppRoutes.PROFILE) { ProfileScreen { nav.navigate(AppRoutes.CONNECTION) { launchSingleTop = true } } }
        if (BuildConfig.DEBUG) composable(AppRoutes.CONNECTION) { ConnectionScreen() }
    }
}

@Composable private fun HomeScreen(openBusiness: () -> Unit) = PageContent {
    Text("随时开启你的工作", style = MaterialTheme.typography.headlineMedium)
    Text("项目、融资与协同，一个入口。", color = MaterialTheme.colorScheme.onSurfaceVariant)
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
        Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Icon(Icons.Outlined.Home, null, Modifier.size(36.dp)); Text("你的移动工作台", style = MaterialTheme.typography.titleLarge)
            Text("项目台账演示已就绪。"); Button(openBusiness) { Text("进入业务") }
        }
    }
}

@Composable private fun ProfileScreen(openConnection: () -> Unit) = PageContent {
    Text("欢迎使用 CityBond", style = MaterialTheme.typography.headlineSmall); Text("账号功能尚未接入。")
    if (BuildConfig.DEBUG) OutlinedCard(openConnection, Modifier.fillMaxWidth().testTag("open_connection")) {
        Column(Modifier.padding(20.dp)) { Text("开发工具 · 连接检查"); Text("验证开发服务器连接") }
    }
    Text("版本 ${BuildConfig.VERSION_NAME}", style = MaterialTheme.typography.labelMedium)
}

@Composable private fun PlaceholderPage(title: String, description: String, icon: ImageVector) = PageContent {
    Icon(icon, null, Modifier.padding(top = 48.dp).size(48.dp), tint = MaterialTheme.colorScheme.primary)
    Text(title, style = MaterialTheme.typography.headlineSmall); Text(description, color = MaterialTheme.colorScheme.onSurfaceVariant)
}

@Composable internal fun PageContent(content: @Composable ColumnScope.() -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp), content = content)
}
