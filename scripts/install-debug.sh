#!/usr/bin/env bash
set -euo pipefail
source "$(dirname "$0")/env.sh"
MOBILE_DEVICE="${1:-emulator-5558}"
MOBILE_APK="$MOBILE_ROOT/app/build/outputs/apk/debug/app-debug.apk"
if [[ ! -f "$MOBILE_APK" ]]; then
    echo "请先运行 ./mobilew :app:assembleDebug" >&2
    exit 1
fi
if [[ "$(adb -s "$MOBILE_DEVICE" shell getprop sys.boot_completed | tr -d '\r')" != "1" ]]; then
    echo "模拟器尚未完成启动，请稍后重试。" >&2
    exit 1
fi
adb -s "$MOBILE_DEVICE" install -r "$MOBILE_APK"
adb -s "$MOBILE_DEVICE" reverse tcp:18080 tcp:18080
adb -s "$MOBILE_DEVICE" shell am start -W -n com.citybond.mobile.debug/com.citybond.mobile.MainActivity
