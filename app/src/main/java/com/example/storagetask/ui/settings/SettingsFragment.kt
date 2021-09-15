package com.example.storagetask.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.storagetask.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}