#!/usr/bin/env bash
# Source this file from WSL; all tool/cache paths remain inside the project.
MOBILE_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
if [[ -x "$MOBILE_ROOT/.tooling/jdk/current/bin/java" ]]; then
    export JAVA_HOME="$MOBILE_ROOT/.tooling/jdk/current"
    export PATH="$JAVA_HOME/bin:$PATH"
fi
export ANDROID_HOME="${ANDROID_HOME:-$MOBILE_ROOT/.tooling/android-sdk}"
export ANDROID_USER_HOME="$MOBILE_ROOT/.tooling/android-user"
export ANDROID_EMULATOR_HOME="$ANDROID_USER_HOME"
export ANDROID_AVD_HOME="$ANDROID_USER_HOME/avd"
export GRADLE_USER_HOME="$MOBILE_ROOT/.tooling/gradle-home"
export PATH="$ANDROID_HOME/platform-tools:$ANDROID_HOME/cmdline-tools/latest/bin:$PATH"
