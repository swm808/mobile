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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.citybond.mobile.BuildConfig

private enum class Destination(val route: String, val label: String, val icon: ImageVector) {
    HOME("home", "首页", Icons.Outlined.Home),
    BUSINESS("business", "业务", Icons.Outlined.Menu),
    ASSISTANT("assistant", "助手", Icons.Outlined.Create),
    TASKS("tasks", "任务", Icons.AutoMirrored.Outlined.List),
    PROFILE("profile", "我的", Icons.Outlined.Person),
}
private const val CONNECTION_ROUTE = "connection"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityBondApp() {
    val navController = rememberNavController()
    val entry by navController.currentBackStackEntryAsState()
    val route = entry?.destination?.route
    val isConnection = route == CONNECTION_ROUTE
    val selected = Destination.entries.firstOrNull { it.route == route } ?: Destination.HOME
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
                        Text(if (isConnection) "连接检查" else selected.label)
                        if (!isConnection) Text("CITYBOND", style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary)
                    }
                },
                navigationIcon = {
                    if (isConnection) IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "返回")
                    }
                },
            )
        },
        bottomBar = {
            if (!isConnection) NavigationBar(containerColor = MaterialTheme.colorScheme.surfaceContainer) {
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
        },
    ) { padding ->
        NavHost(navController, startDestination = Destination.HOME.route, modifier = Modifier.padding(padding)) {
            composable(Destination.HOME.route) {
                PageContent {
                    Text("随时开启你的工作", style = MaterialTheme.typography.headlineMedium)
                    Text("项目、融资与协同，一个入口。", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                        Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            Icon(Icons.Outlined.Home, contentDescription = null, modifier = Modifier.size(36.dp))
                            Text("你的移动工作台", style = MaterialTheme.typography.titleLarge)
                            Text("基础框架已就绪，业务功能将分批接入。")
                            Button(onClick = { openTab(Destination.BUSINESS) }) { Text("进入业务") }
                        }
                    }
                    Text("快捷入口", style = MaterialTheme.typography.titleMedium)
                    OutlinedCard(onClick = { openTab(Destination.TASKS) }, modifier = Modifier.fillMaxWidth()) {
                        Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            Icon(Icons.AutoMirrored.Outlined.List, contentDescription = null)
                            Column {
                                Text("任务中心", style = MaterialTheme.typography.titleMedium)
                                Text("查看任务与处理进度", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
            composable(Destination.BUSINESS.route) {
                PlaceholderPage("业务入口", "项目、融资及协同功能将在后续批次接入。", Icons.Outlined.Menu)
            }
            composable(Destination.ASSISTANT.route) {
                PlaceholderPage("助手尚未接入", "对话功能将在独立批次开发。", Icons.Outlined.Create)
            }
            composable(Destination.TASKS.route) {
                PlaceholderPage("任务服务尚未接入", "后续可在这里跟踪上传、识别与导出进度。", Icons.AutoMirrored.Outlined.List)
            }
            composable(Destination.PROFILE.route) {
                PageContent {
                    Text("欢迎使用 CityBond", style = MaterialTheme.typography.headlineSmall)
                    Text("账号功能尚未接入。", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    if (BuildConfig.DEBUG) OutlinedCard(
                        onClick = { navController.navigate(CONNECTION_ROUTE) { launchSingleTop = true } },
                        modifier = Modifier.fillMaxWidth().testTag("open_connection"),
                    ) {
                        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("开发工具 · 连接检查", style = MaterialTheme.typography.titleMedium)
                            Text("验证开发服务器连接", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                    Text("版本 ${BuildConfig.VERSION_NAME}", style = MaterialTheme.typography.labelMedium)
                }
            }
            if (BuildConfig.DEBUG) composable(CONNECTION_ROUTE) { ConnectionScreen() }
        }
    }
}

@Composable
private fun PlaceholderPage(title: String, description: String, icon: ImageVector) {
    PageContent {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 48.dp).size(48.dp))
        Text(title, style = MaterialTheme.typography.headlineSmall)
        Text(description, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
internal fun PageContent(content: @Composable ColumnScope.() -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp), content = content)
}
