package com.ap.pdfviewerappbookxpert

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import com.ap.pdfviewerappbookxpert.utils.DataStoreManager
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PdfViewerBookExpertApplication : Application() {
    companion object {
        lateinit var instance: PdfViewerBookExpertApplication
            private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this

        FirebaseApp.initializeApp(applicationContext)
            DataStoreManager.applyTheme(this@PdfViewerBookExpertApplication)

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "delete_channel"
            val channelName = "Item Deletion Notifications"
            val channelDescription = "Notifications for item deletion"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val notificationChannel = NotificationChannel(channelId, channelName, importance)
            notificationChannel.description = channelDescription

            // Register the channel with the system
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}
