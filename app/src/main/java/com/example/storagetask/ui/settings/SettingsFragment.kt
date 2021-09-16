package com.example.storagetask.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceFragmentCompat
import com.example.storagetask.R
import com.example.storagetask.container.AppContainer

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val prefsHelper by lazy { AppContainer.preferences!! }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        key?.also {
            prefsHelper.reportChange(key)
        }
    }
}