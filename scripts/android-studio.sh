#!/usr/bin/env bash
set -euo pipefail

MOBILE_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
STUDIO_HOME="$MOBILE_ROOT/.tooling/android-studio"
STUDIO_STATE="$MOBILE_ROOT/.tooling/android-studio-user"
STUDIO_MEMORY_OPTIONS="$MOBILE_ROOT/scripts/android-studio.vmoptions"

if [[ ! -x "$STUDIO_HOME/bin/studio.sh" ]]; then
    cat >&2 <<'EOF'
Android Studio 尚未安装到 .tooling/android-studio。
请先从 Android Developers 官方页面下载 Linux 版，并解压到上述目录。
EOF
    exit 1
fi

# 复用项目内 Android SDK/JDK，不修改 shell 全局配置。
source "$MOBILE_ROOT/scripts/env.sh"
export ANDROID_SDK_ROOT="$ANDROID_HOME"
export STUDIO_VM_OPTIONS="$STUDIO_MEMORY_OPTIONS"
export XDG_CONFIG_HOME="$STUDIO_STATE/config"
export XDG_CACHE_HOME="$STUDIO_STATE/cache"
export XDG_DATA_HOME="$STUDIO_STATE/data"
export XDG_STATE_HOME="$STUDIO_STATE/state"
mkdir -p "$XDG_CONFIG_HOME" "$XDG_CACHE_HOME" "$XDG_DATA_HOME" "$XDG_STATE_HOME"

exec "$STUDIO_HOME/bin/studio.sh" "$MOBILE_ROOT"
