# FOFF — Feckin Off!

> One tap to power off your phone. No more fumbling.

FOFF is a minimal Android utility that opens the system power menu (Power Off, Restart, Emergency, Lockdown) with a single tap — born out of sheer frustration at Android's habit of burying the power button behind gestures, hold timers, and menus that move between OS versions.

Channeling that inner Celtic rage since 2026.

---

## What It Does

Tapping the **POWER MENU** card triggers the native Android system power overlay — the same one you'd get by holding the physical power button. That overlay gives you:

- Power Off
- Restart
- Emergency
- Lockdown

FOFF doesn't implement its own shutdown logic. It calls `performGlobalAction(GLOBAL_ACTION_POWER_DIALOG)` via Android's Accessibility API, which means Android handles all the actual actions. FOFF is just the shortcut.

---

## Requirements

| | |
|---|---|
| Android | 8.0 (API 26) or higher |
| Permission | Accessibility Service |
| Internet | Not required — zero network access |

---

## How to Install

### Option A — Build from source

1. **Prerequisites**
   - JDK 21 (`JAVA_HOME` pointing to it)
   - Android SDK with `platforms;android-35` and `build-tools;35.0.0`
   - `ANDROID_HOME` set to your SDK path

2. **Clone and build**
   ```powershell
   git clone https://github.com/yourname/kotlin-fof.git
   cd kotlin-fof/FOF
   .\gradlew.bat assembleDebug
   ```

3. **Install via ADB**
   ```powershell
   adb install app\build\outputs\apk\debug\fof-debug.apk
   ```

### Option B — Download from GitHub Releases

1. Go to the [Releases page](../../releases/latest)
2. Download `fof-release.apk` from the release assets
3. Enable **Install from unknown sources** on your device
4. Tap the downloaded APK to install

> Verify the APK signature before installing — see [SECURITY.md](SECURITY.md) for the `apksigner verify` command and expected certificate fingerprint.

---

## Setup (First Launch)

FOFF requires the **FOFF Power Service** accessibility permission to function.

1. Open FOFF
2. Tap the **POWER MENU** card — if the service isn't enabled, you'll be taken directly to Accessibility Settings
3. Find **FOFF Power Service** and toggle it on
4. Return to FOFF — the card now works immediately

> **Note:** Android may periodically revoke accessibility permissions after OS updates or battery optimisation changes. If the card stops working, re-enable the service via Settings → Accessibility.

---

## Project Structure

```
FOF/
├── app/src/main/
│   ├── java/com/fof/fof/
│   │   ├── MainActivity.kt               — UI, card tap, WHY FOFF? dialog
│   │   └── FofAccessibilityService.kt    — Accessibility service, power dialog trigger
│   ├── res/
│   │   ├── layout/activity_main.xml      — Dark card UI layout
│   │   ├── values/themes.xml             — Monochrome Material theme + dialog style
│   │   ├── values/strings.xml            — App name, service label
│   │   ├── drawable/ic_power_off.xml     — Power icon
│   │   └── xml/accessibility_service_config.xml
│   └── AndroidManifest.xml
├── build.gradle.kts
├── app/build.gradle.kts
├── settings.gradle.kts
└── gradle/libs.versions.toml
```

---

## Tech Stack

| Component | Version |
|---|---|
| Kotlin | 1.9.22 |
| Android Gradle Plugin | 8.2.2 |
| Gradle | 8.5 |
| compileSdk / targetSdk | 35 |
| minSdk | 26 (Android 8.0) |
| Material Components | 1.11.0 |
| AppCompat | 1.6.1 |
| ConstraintLayout | 2.1.4 |

---

## How It Works (Technical)

`FofAccessibilityService` extends Android's `AccessibilityService`. It holds a static instance of itself in a `companion object`, set on `onServiceConnected()` and cleared on `onDestroy()`. This lets `MainActivity` check `isRunning()` and call `triggerPowerDialog()` without binding to the service directly (bound services cannot trigger global actions).

```kotlin
// FofAccessibilityService.kt — core pattern
companion object {
    private var instance: FofAccessibilityService? = null
    fun isRunning(): Boolean = instance != null
    fun triggerPowerDialog(): Boolean =
        instance?.performGlobalAction(GLOBAL_ACTION_POWER_DIALOG) ?: false
}
```

The UI is a single `MaterialCardView` that acts as the full tap target. There are no additional permissions beyond `BIND_ACCESSIBILITY_SERVICE`, no network calls, no analytics, no data collection of any kind.

---

## Known Limitations

- **Android security changes:** Google periodically tightens restrictions on `AccessibilityService` usage in background apps. Future Android versions may require additional steps or may restrict this approach entirely.
- **OEM variations:** Some manufacturers (Samsung One UI, MIUI, etc.) customise the power overlay behaviour — results may vary.
- **Service restarts:** Aggressive battery optimisation on some devices can kill the service. Exempt FOFF from battery optimisation in device settings if this occurs.

---

## License

This project is licensed under the **Creative Commons Attribution-NonCommercial 4.0 International (CC BY-NC 4.0)** license.

You are free to use, share, and adapt this project for non-commercial purposes with attribution. Commercial use is not permitted.

See [LEGAL.md](LEGAL.md) for full terms.

---

## Security

See [SECURITY.md](SECURITY.md) for the vulnerability reporting policy, dependency update process, and APK verification instructions.

---

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md).
