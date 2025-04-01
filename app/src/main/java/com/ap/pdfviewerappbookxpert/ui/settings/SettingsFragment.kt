package com.ap.pdfviewerappbookxpert.ui.settings

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.ap.pdfviewerappbookxpert.R
import com.ap.pdfviewerappbookxpert.utils.DataStoreManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switchTheme = view.findViewById<SwitchCompat>(R.id.switch_theme)
        val switchNotifications = view.findViewById<SwitchCompat>(R.id.switch_notifications)

        // Get and observe theme preference
        lifecycleScope.launch {
            DataStoreManager.getNotificationPreference(requireContext()).collect { isON ->
                switchNotifications.isChecked = isON
            }
        }

        // Get and observe theme preference
        lifecycleScope.launch {
            DataStoreManager.getThemePreference(requireContext()).collect { isDarkMode ->
                switchTheme.isChecked = isDarkMode
            }
        }

        // Handle switch change
        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                DataStoreManager.saveThemePreference(requireContext(), isChecked)
                DataStoreManager.applyTheme(requireContext())
            }
        }

        switchNotifications.setOnCheckedChangeListener { _, isChecked ->

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Enable notification permission in settings", Toast.LENGTH_SHORT).show()
                switchNotifications.isChecked = false
                return@setOnCheckedChangeListener
            }else{
                lifecycleScope.launch {
                    DataStoreManager.saveNotificationPreference(requireContext(), isChecked)
                }
            }

        }

    }
}