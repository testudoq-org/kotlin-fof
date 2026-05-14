package com.fof.fof

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<MaterialButton>(R.id.btn_power_off).setOnClickListener {
            triggerPowerAction()
        }

        findViewById<MaterialButton>(R.id.btn_restart).setOnClickListener {
            triggerPowerAction()
        }
    }

    private fun triggerPowerAction() {
        if (!FofAccessibilityService.isRunning()) {
            Toast.makeText(this, "Please enable FOF Power Service in Accessibility settings", Toast.LENGTH_LONG).show()
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            return
        }
        val success = FofAccessibilityService.triggerPowerDialog()
        if (!success) {
            Toast.makeText(this, "Failed to open power menu. Try toggling the service off/on.", Toast.LENGTH_SHORT).show()
        }
    }
}
