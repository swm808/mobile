#!/usr/bin/env bash
set -euo pipefail
source "$(dirname "$0")/env.sh"
if [[ -e /dev/kvm && ! -w /dev/kvm ]] && id -nG "$(id -un)" | tr ' ' '\n' | grep -qx kvm; then
    # Apply newly granted group membership without restarting the user's WSL session.
    printf -v EMULATOR_COMMAND '%q ' "$MOBILE_ROOT/scripts/start-emulator.sh" "$@"
    exec sg kvm -c "$EMULATOR_COMMAND"
fi
export ANDROID_AVD_HOME="$MOBILE_ROOT/.tooling/android-user/avd"
export LD_LIBRARY_PATH="$MOBILE_ROOT/.tooling/linux-libs/usr/lib/x86_64-linux-gnu:$MOBILE_ROOT/.tooling/linux-libs/usr/lib/x86_64-linux-gnu/pulseaudio${LD_LIBRARY_PATH:+:$LD_LIBRARY_PATH}"
EMULATOR_ACCEL=auto
if [[ ! -r /dev/kvm || ! -w /dev/kvm ]]; then
    EMULATOR_ACCEL=off
    echo "KVM 不可用，使用软件模拟；首次启动可能较慢。"
fi
exec "$ANDROID_HOME/emulator/emulator" -avd CityBond_API_30 -port 5558 \
    -accel "$EMULATOR_ACCEL" -gpu swiftshader -no-audio -no-snapshot -no-boot-anim -no-metrics \
    -camera-back none -camera-front none "$@"
