**Perfect.** Here's everything you need to build your **FOF** app (Power Off + Restart) using **VS Code + Android CLI**.

---

### 1. First – Create the Android Project via CLI

Open **PowerShell** or **Terminal** and run these commands:

```powershell
# 1. Go to your projects folder
cd ~D:\Code\GitHub\kotlin-fof

# 2. Create new Android project (Kotlin)
gradle init --type basic --dsl kotlin --project-dir FOF

# Better way - use the official template:
mkdir FOF && cd FOF

# Use this command to create a proper Android project:
gradle -q init --type android-application --dsl kotlin --project-dir .
```

**Easier recommended method** (if the above feels messy):

Go to this link and download a basic template, or run:

```powershell
sdkmanager "platforms;android-35" "build-tools;35.0.0" "platform-tools"
```

Then I’ll give you the manual folder structure below.

---

### 2. Best Way: Project Structure & Code (Copy-Paste)

Create this folder structure inside your `FOF` folder:

```
FOF/
├── app/
│   ├── src/main/
│   │   ├── java/com/fof/fof/
│   │   │   ├── MainActivity.kt
│   │   │   └── FofAccessibilityService.kt
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml
│   │   │   └── xml/
│   │   │       └── accessibility_service_config.xml
│   │   └── AndroidManifest.xml
├── build.gradle.kts
├── app/build.gradle.kts
├── settings.gradle.kts
└── gradlew.bat
```

---

### 3. Prompts You Can Give to GitHub Copilot / Cursor / Any AI

**Prompt 1 – Project Setup**
> Create a minimal Android project in Kotlin for an app called "FOF" with package name `com.fof.fof`. It should have two big buttons: "POWER OFF" (red) and "RESTART" (blue). Use Material3 design.

**Prompt 2 – Main Screen**
> Write activity_main.xml with a dark theme, centered vertical layout, two large buttons (320dp width, 160dp height) for Power Off and Restart.

**Prompt 3 – Accessibility Service**
> Create FofAccessibilityService.kt that extends AccessibilityService and has a companion object with ACTION_SHOW_POWER_DIALOG. In onStartCommand, call performGlobalAction(GLOBAL_ACTION_POWER_DIALOG).

**Prompt 4 – MainActivity**
> Write MainActivity.kt that starts the Accessibility Service when either button is clicked.

---

### 4. Ready-to-Use Code

#### `app/src/main/res/layout/activity_main.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:background="#0F0F0F">

    <Button
        android:id="@+id/btn_power_off"
        android:layout_width="320dp"
        android:layout_height="160dp"
        android:text="POWER OFF"
        android:textSize="28sp"
        android:textColor="#FFFFFF"
        android:backgroundTint="#D32F2F" />

    <Button
        android:id="@+id/btn_restart"
        android:layout_width="320dp"
        android:layout_height="160dp"
        android:text="RESTART"
        android:textSize="28sp"
        android:textColor="#FFFFFF"
        android:layout_marginTop="48dp"
        android:backgroundTint="#1E88E5" />

</LinearLayout>
```

#### `app/src/main/java/com/fof/fof/MainActivity.kt`
```kotlin
package com.fof.fof

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_power_off).setOnClickListener { triggerPowerMenu() }
        findViewById<Button>(R.id.btn_restart).setOnClickListener { triggerPowerMenu() }
    }

    private fun triggerPowerMenu() {
        val intent = Intent(this, FofAccessibilityService::class.java).apply {
            action = FofAccessibilityService.ACTION_SHOW_POWER_DIALOG
        }
        startService(intent)
        Toast.makeText(this, "Opening system power menu...", Toast.LENGTH_SHORT).show()
    }
}
```

#### `app/src/main/java/com/fof/fof/FofAccessibilityService.kt`
```kotlin
package com.fof.fof

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent

class FofAccessibilityService : AccessibilityService() {

    companion object {
        const val ACTION_SHOW_POWER_DIALOG = "com.fof.fof.SHOW_POWER_DIALOG"
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {}

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_SHOW_POWER_DIALOG) {
            performGlobalAction(GLOBAL_ACTION_POWER_DIALOG)
        }
        return super.onStartCommand(intent, flags, startId)
    }
}
```

#### `app/src/main/AndroidManifest.xml` (Key part)
Add this inside `<application>`:

```xml
<service
    android:name=".FofAccessibilityService"
    android:label="FOF Power Service"
    android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE"
    android:exported="false">
    
    <intent-filter>
        <action android:name="android.accessibilityservice.AccessibilityService" />
    </intent-filter>
    
    <meta-data
        android:name="android.accessibilityservice"
        android:resource="@xml/accessibility_service_config" />
</service>
```

---

### 5. How to Build the APK (Final Step)

In the root of your `FOF` project, run these commands:

```powershell
# Make gradlew executable (first time only)
chmod +x gradlew

# Build debug APK
./gradlew assembleDebug
```

The APK will be here:
```
app/build/outputs/apk/debug/app-debug.apk
```

**Install on your phone:**
```powershell
adb install app/build/outputs/apk/debug/app-debug.apk
```

---
