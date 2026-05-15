package com.fof.fof

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
        MaterialAlertDialogBuilder(this)
            .setTitle("FOFF – Feckin off!")
            .setMessage(
                "A quick tool to Power Off & Restart.\n\n" +
                "Requires accessibility approval and over time may be restricted " +
                "due to Android's annoyance of changing security protocols.\n\n" +
                "Tired of fumbling through menus just to turn your damn phone off?\n\n" +
                "FOFF gives you two big, simple buttons: Power Off and Restart.\n\n" +
                "No more nonsense. No more hunting for the power menu.\n\n" +
                "Just tap and done.\n\n" +
                "Channeling that inner Celtic rage since 2026.\n\n" +
                "Feckin Off!"
            )
            .setPositiveButton("Feckin right") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
