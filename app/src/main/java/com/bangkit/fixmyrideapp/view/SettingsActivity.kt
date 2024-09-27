package com.bangkit.fixmyrideapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.bangkit.fixmyrideapp.R
import com.bangkit.fixmyrideapp.data.utils.DarkMode
import java.util.Locale

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            findPreference<ListPreference>(getString(R.string.pref_key_dark))?.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener{ _, newValue ->
                    newValue.let {
                        val selectedItem =
                            when((it as String).uppercase(Locale.ROOT)){
                                DarkMode.ON.name -> DarkMode.ON
                                DarkMode.OFF.name -> DarkMode.OFF
                                else -> DarkMode.FOLLOW_SYSTEM
                            }
                        updateTheme(selectedItem.value)
                    }
                    true
                }
        }

        private fun updateTheme(mode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(mode)
            requireActivity().recreate()
            return true
        }
    }

}