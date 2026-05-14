package com.fof.fof

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Entire card triggers the power menu
        findViewById<MaterialCardView>(R.id.card_power_menu).setOnClickListener {
            triggerPowerAction()
        }

        // WHY FOFF? — backstory dialog
        findViewById<MaterialButton>(R.id.btn_why_foff).setOnClickListener {
            showWhyFoffDialog()
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

    private fun showWhyFoffDialog() {
        AlertDialog.Builder(this, R.style.Theme_FOF_Dialog)
            .setTitle("Why FOFF?")
            .setMessage(
                "FOFF stands for Feck Off.\n\n" +
                "Born out of pure frustration — fumbling through menus, " +
                "swiping the wrong way, desperately hunting for the power " +
                "button at 2am while your phone refuses to cooperate.\n\n" +
                "One tap. That's all it should ever take."
            )
            .setPositiveButton("Fair enough") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
