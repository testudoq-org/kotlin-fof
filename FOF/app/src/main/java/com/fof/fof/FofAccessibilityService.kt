package com.fof.fof

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class FofAccessibilityService : AccessibilityService() {

    companion object {
        private var instance: FofAccessibilityService? = null

        fun isRunning(): Boolean = instance != null

        fun triggerPowerDialog(): Boolean =
            instance?.performGlobalAction(GLOBAL_ACTION_POWER_DIALOG) ?: false
    }

    override fun onServiceConnected() {
        instance = this
    }

    override fun onDestroy() {
        instance = null
        super.onDestroy()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {}
}
