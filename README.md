# CityBond Android

当前已完成基础框架、核心数据模型、项目台账与债务管理 UI 演示。业务页面使用本地内存数据；没有登录、聊天或正式业务写入。

项目目录：`/home/tdy/projects/mobile`。本机使用 **VS Code + WSL 命令行构建 + 官方 Android 模拟器**。Android Studio IDE 未在本批安装。

**已配置的技术栈**

| 项目 | 版本 / 说明 |
| --- | --- |
| JDK | Eclipse Temurin 17.0.20.1，项目内安装 |
| Gradle / AGP | Wrapper 8.13 / Android Gradle Plugin 8.13.2 |
| Kotlin | 2.2.20，Compose 与 Serialization 插件同版本 |
| Android | minSdk 26，compileSdk / targetSdk 36，Build Tools 35.0.0 |
| UI | Compose BOM 2025.09.01、Material 3、浅色/深色主题 |
| 路由 | Navigation Compose 2.9.5，首页 / 业务 / 助手 / 任务 / 我的 |
| 网络 | Retrofit 3.0.0、OkHttp 4.12.0、Kotlin Serialization 1.9.0 |
| 本机模拟器 | `CityBond_API_30`，Android 11 / API 30，720 × 1280，KVM 硬件加速 |
| 应用标识 | `com.citybond.mobile`；调试版为 `com.citybond.mobile.debug`，可在下一批按正式包名调整 |

版本作为已验证组合固定，不使用动态版本。Gradle Wrapper 包含官方 SHA-256 校验值。

**在本机编译和运行**

在 VS Code 的 WSL 终端中执行：

```bash
cd /home/tdy/projects/mobile
./mobilew :app:assembleDebug
```

`mobilew` 自动加载 `scripts/env.sh`，使用项目内 JDK、SDK 和 Gradle 缓存，不修改系统 PATH 或 shell 配置。首次解析依赖需要联网。其他已配置 JDK/SDK 的标准 Android 环境可直接使用 `./gradlew`。

打开一个终端启动模拟器，并保持该终端运行：

```bash
cd /home/tdy/projects/mobile
./scripts/start-emulator.sh
```

首次启动需等待 Android 系统初始化。已将本机用户加入 `kvm` 组并启用硬件加速。当前终端尚未刷新用户组时，脚本通过 `sg kvm` 应用组权限；没有可用 KVM 的其他环境才回退软件模拟。不要反复启动第二个实例。ADB 使用本项目 Linux 版本，与 Windows 腾讯模拟器的 ADB 独立。

在另一个终端检查设备并安装：

```bash
cd /home/tdy/projects/mobile
source scripts/env.sh
adb devices -l
adb -s emulator-5558 shell getprop sys.boot_completed
./scripts/install-debug.sh
```

`sys.boot_completed` 返回 `1` 后才能安装。安装脚本将已有调试 APK 安装到 `emulator-5558`，映射本机 18080 端口并启动 CityBond。以后修改代码时重新编译，再运行安装脚本；支持将其他设备序列号作为第一个参数。

APK 输出：[app-debug.apk](app/build/outputs/apk/debug/app-debug.apk)。若文件尚未生成，先执行编译命令。

关闭本次官方模拟器：

```bash
source scripts/env.sh
adb -s emulator-5558 emu kill
```

**验证网络请求库**

调试版入口：我的 → 开发工具 · 连接检查。页面仅向输入的服务根地址发送 `GET /health`，期望返回 `{"status":"ok"}`；不会发送账号、聊天或业务数据。

- 默认地址 `http://127.0.0.1:18080/`，通过安装脚本中的 `adb reverse` 访问 WSL 本机 18080。
- 如果真实后端运行于 WSL 的 8000 端口，可执行 `adb -s emulator-5558 reverse tcp:18080 tcp:8000`，然后继续使用默认地址。
- 其他 HTTPS 服务可输入其根地址。不要填写 `/api/v1/`，现有 CityBond 健康检查在根路径 `/health`。
- 调试版 HTTP 仅放行 localhost、127.0.0.1、10.0.2.2；正式版保持 HTTPS。没有加入全局信任所有证书或请求正文日志。
- 基础框架仅有健康检查，不宣称完成真实业务接口联调。

需要独立验证传输时，可在 WSL 启动本地临时示例服务：

```bash
mkdir -p .tooling/smoke-http
printf '{"status":"ok","service":"local-network-smoke-test"}\n' > .tooling/smoke-http/health
python3 -m http.server 18080 --bind 127.0.0.1 --directory .tooling/smoke-http
```

示例服务只验证请求链路，不是真实 CityBond 后端。已有人使用 18080 时不要重复启动；运行服务的终端按 Ctrl+C 停止。

**检查命令**

```bash
./mobilew :app:testDebugUnitTest :app:lintDebug
./mobilew :app:assembleDebugAndroidTest
# 已启动且可用的 Android 设备上运行路由测试：
./mobilew :app:connectedDebugAndroidTest
```

网络单测覆盖健康检查路径/解析、不发送认证信息、HTTP 错误不重试、响应缺字段以及不接受携带凭证的服务地址。设备测试覆盖五个导航入口、连接检查页返回、项目深层路由与空表单校验。

在软件模拟器与 Gradle 同时运行导致内存紧张时，可先构建完，再 `./mobilew --stop` 释放构建后台内存，然后安装和操作模拟器。

**代码位置**

| 位置 | 职责 |
| --- | --- |
| `app/src/main/java/com/citybond/mobile/MainActivity.kt` | Activity 与 Compose 入口 |
| `.../ui/CityBondApp.kt` | 应用框架、五个一级入口与 NavHost |
| `.../ui/AppRoutes.kt` | 集中的顶层、项目与债务深层路由配置 |
| `.../ui/ProjectScreens.kt` | 项目目录、筛选列表、分栏详情及新建/编辑表单 |
| `.../ui/ProjectComponents.kt` | 项目卡片、阶段标签、信息行和内容分区组件 |
| `.../ui/ProjectUiModels.kt` | 项目管理展示模型与运行期演示数据 |
| `.../ui/DebtScreens.kt` | 债务台账、待完善列表、分栏详情及三步表单 |
| `.../ui/DebtComponents.kt` | 债务卡片、状态标签与展示名称组件 |
| `.../ui/DebtUiModels.kt` | 债务管理展示模型与运行期演示数据 |
| `.../ui/Theme.kt` | 浅色/深色主题 |
| `.../ui/ConnectionScreen.kt` | 开发用连接检查及 ViewModel 状态 |
| `.../network/NetworkClient.kt` | 可复用 OkHttp、Retrofit 与健康检查合同 |
| `app/src/debug/` | 仅调试版使用的本机 HTTP 放行配置 |
| `app/src/test/`、`app/src/androidTest/` | 网络单测、设备路由测试 |
| `gradle/libs.versions.toml` | 依赖与插件版本统一管理 |
| `scripts/`、`mobilew` | WSL 环境、模拟器启动、安装与构建入口 |
| `.tooling/` | 本机 SDK、JDK、模拟器、依赖缓存及验证文件，已被 Git 忽略 |

没有实现 Cookie 登录；后续登录批次再加入相应会话管理。当前只做轻量手工依赖组装，业务增长后再引入需要的 DI 或多模块拆分。

**文档与批次约束**

- [开发进度与验证结果](docs/开发进度.md)
- [核心数据模型与 Mock 使用说明](app/src/debug/java/com/citybond/mobile/mock/README.md)
- [技术栈与业务模块对照（学习速查）](docs/技术栈与业务模块对照.md)
- [小批次开发约定](AGENTS.md)
- [移动端需求文档](docs/移动端需求文档.md)
- [业务重难点与全功能展示方案](docs/Android业务重难点与全功能展示方案.md)
- [develop 后端静态路由清单](docs/develop后端静态路由清单.md)

本项目已初始化独立 Git 仓库。SDK、缓存、APK、AVD、`local.properties` 和签名材料不提交。换机器需重新安装对应工具；不要把当前 Linux SDK 路径直接交给 Windows 版 Android Studio 使用。
