# CityBond Android

当前保留基础框架、核心数据模型和开发用连接检查。旧的项目、债务、日历统计与 AI 文档 Mock UI 已移除；已按远端 develop `a7da695f` 的运行时 OpenAPI 与权限目录 v3 同步 16 个菜单模块、33 个权限功能组。融资总览已搭建无 Mock 数值的 UI 与子路由，其余目录仍是只读覆盖清单；目前没有登录、聊天或正式业务读写。

项目目录：`/home/tdy/projects/mobile`。本机使用 **VS Code + WSL 命令行构建**；Android Studio 仅用于 Compose Preview，官方 Android 模拟器集中用于批次收尾验证。

**已配置的技术栈**

| 项目 | 版本 / 说明 |
| --- | --- |
| JDK | Eclipse Temurin 17.0.20.1，项目内安装 |
| Gradle / AGP | Wrapper 8.13 / Android Gradle Plugin 8.13.2 |
| Kotlin | 2.2.20，Compose 与 Serialization 插件同版本 |
| Android | minSdk 26，compileSdk / targetSdk 36，Build Tools 35.0.0 |
| UI | Compose BOM 2025.09.01、Material 3、浅色/深色主题 |
| Compose Preview | Android Studio Quail 4 / 2026.1.4 Patch 1，项目内安装，IDE 最大堆 1.5 GiB |
| 路由 | Navigation Compose 2.9.5，五个一级入口及融资总览、公告、待办、每日到账、月份还款、项目进度子路由 |
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

**Compose 无模拟器预览**

日常编辑仍使用 VS Code；Android Studio 仅用于官方 Compose Preview，不需要启动 AVD。已校验安装的 Linux 版 Android Studio 位于已忽略的 `.tooling/android-studio/`，IDE 配置与缓存放在 `.tooling/android-studio-user/`；启动脚本将 IDE 最大堆限制为 1.5 GiB。启动：

```bash
cd /home/tdy/projects/mobile
./scripts/android-studio.sh
```

首次启动时在 Android Studio 的许可/初始化界面中由使用者自行确认；不要新建 SDK 或 AVD，项目会复用 `local.properties` 指向的 `.tooling/android-sdk`。等待 Gradle Sync 完成后，打开 `app/src/debug/java/com/citybond/mobile/ui/ComposePreviews.kt`，在编辑器右上角切换到 **Split** 或 **Design**。建议使用 Preview 的 Focus 模式，一次只渲染一个页面。

预览入口仅位于 debug 源集，当前包含融资总览的浅色/深色状态、业务目录和综合台账。它们不请求网络、不构造 ViewModel，也不进入 Release APK。Preview 只用于布局与静态状态检查；路由、键盘、权限、网络和真实交互仍在每批收尾时集中用模拟器验证。

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

网络单测覆盖健康检查路径/解析、不发送认证信息、HTTP 错误不重试、响应缺字段以及不接受携带凭证的服务地址。设备测试覆盖五个导航入口、业务重建状态、连接检查页，以及融资总览的入口、参数化子路由与返回栈。

在软件模拟器与 Gradle 同时运行导致内存紧张时，可先构建完，再 `./mobilew --stop` 释放构建后台内存，然后安装和操作模拟器。

**代码位置**

| 位置 | 职责 |
| --- | --- |
| `app/src/main/java/com/citybond/mobile/MainActivity.kt` | Activity 与 Compose 入口 |
| `.../ui/CityBondApp.kt` | 应用框架、五个一级入口与 NavHost |
| `.../ui/BusinessCatalog.kt` | develop 权限目录 v3 的菜单/功能组合同；融资总览可进入 UI，其余模块保持只读状态 |
| `.../ui/AppRoutes.kt` | 一级路由、融资总览子路由、参数名、构建方法及标题配置 |
| `.../ui/FinancingOverviewScreen.kt` | 融资总览与公告、待办、每日到账、月份还款、项目进度空状态 UI |
| `.../ui/Theme.kt` | 浅色/深色主题 |
| `.../ui/ConnectionScreen.kt` | 开发用连接检查及 ViewModel 状态 |
| `app/src/debug/.../ui/ComposePreviews.kt` | 不进入 Release 的融资总览、业务目录和综合台账预览入口 |
| `.../network/NetworkClient.kt` | 可复用 OkHttp、Retrofit 与健康检查合同 |
| `app/src/debug/` | 仅调试版使用的本机 HTTP 放行配置 |
| `app/src/test/`、`app/src/androidTest/` | 网络单测、设备路由测试 |
| `gradle/libs.versions.toml` | 依赖与插件版本统一管理 |
| `scripts/`、`mobilew` | WSL 环境、Android Studio Preview、模拟器启动、安装与构建入口 |
| `.tooling/` | 本机 SDK、JDK、模拟器、依赖缓存及验证文件，已被 Git 忽略 |

没有实现 Cookie 登录；后续登录批次再加入相应会话管理。当前只做轻量手工依赖组装，业务增长后再引入需要的 DI 或多模块拆分。

**文档与批次约束**

- [开发进度与验证结果](docs/开发进度.md)
- [核心数据模型与 Mock 使用说明](app/src/debug/java/com/citybond/mobile/mock/README.md)
- [技术栈与业务模块对照（学习速查）](docs/技术栈与业务模块对照.md)
- [小批次开发约定](AGENTS.md)
- [移动端需求文档](docs/移动端需求文档.md)
- [develop 后端驱动的移动端重建设计](docs/develop后端驱动重建设计.md)
- [业务重难点与全功能展示方案](docs/Android业务重难点与全功能展示方案.md)
- [develop 后端运行时接口合同清单](docs/develop后端接口清单.md)
- [develop 后端静态路由清单](docs/develop后端静态路由清单.md)

本项目已初始化独立 Git 仓库。SDK、缓存、APK、AVD、`local.properties` 和签名材料不提交。换机器需重新安装对应工具；不要把当前 Linux SDK 路径直接交给 Windows 版 Android Studio 使用。
