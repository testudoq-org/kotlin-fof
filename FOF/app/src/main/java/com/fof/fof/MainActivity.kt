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

        findViewById<MaterialButton>(R.id.btn_power_menu).setOnClickListener {
            triggerPowerAction()
        }
    }

    private fun triggerPowerAction() {
        if (!FofAccessibilityService.isRunning()) {
            Toast.makeText(this, "Enable FOFF Power Service in Accessibility settings first", Toast.LENGTH_LONG).show()
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            return
        }
        val success = FofAccessibilityService.triggerPowerDialog()
        if (!success) {
            Toast.makeText(this, "Couldn't open power menu — toggle the service off/on.", Toast.LENGTH_SHORT).show()
        }
    }
}
