package com.example.music_player

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity: AppCompatActivity() {

    private lateinit var ibGoBack: ImageButton
    private lateinit var ibVolumeEnable: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        ibGoBack = findViewById(R.id.ibGoBack)
        ibVolumeEnable = findViewById(R.id.ibVolumeEnable)

        updateUI()

        setUpListeners()
    }

    override fun onPause() {
        super.onPause()
        val pref : SharedPreferences =
            this.getSharedPreferences(this.packageName + "_preferences", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = pref.edit()
        editor.putBoolean(MainActivity.CONFIG, MainActivity.volumeChange)
        editor.apply()
    }

    private fun updateUI() {
        if (MainActivity.volumeChange) {
            ibVolumeEnable.setImageResource(R.drawable.baseline_radio_button_checked_24)
        } else {
            ibVolumeEnable.setImageResource(R.drawable.baseline_radio_button_unchecked_24)
        }
    }

    private fun setUpListeners() {
        ibGoBack.setOnClickListener {
            finish()
        }
        ibVolumeEnable.setOnClickListener {
            if (MainActivity.volumeChange) {
                MainActivity.volumeChange = false
                ibVolumeEnable.setImageResource(R.drawable.baseline_radio_button_unchecked_24)
            } else {
                MainActivity.volumeChange = true
                ibVolumeEnable.setImageResource(R.drawable.baseline_radio_button_checked_24)
            }
        }
    }
}