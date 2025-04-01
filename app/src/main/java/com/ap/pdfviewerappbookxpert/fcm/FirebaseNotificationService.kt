package com.ap.pdfviewerappbookxpert.fcm

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.ap.pdfviewerappbookxpert.R
import com.ap.pdfviewerappbookxpert.utils.DataStoreManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class FirebaseNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        val notification = remoteMessage.notification?.body

//        showNotification(notification)


        val notification = NotificationCompat.Builder(applicationContext, "")
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setContentTitle("Item Deleted")
            .setContentText("Item  has been deleted.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        showNotification("Item Deleted")
//        val notificationManager = applicationContext.no.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.notify(1, notification)

    }
    private fun showNotification(message: String?) {
        val job = CoroutineScope(Dispatchers.IO)
        job.launch {
            DataStoreManager.getNotificationPreference(applicationContext).collect { isON ->
               if (isON) {
                   withContext(Dispatchers.Main){
                       val builder = NotificationCompat.Builder(applicationContext, "default")
                           .setContentTitle("Item Deleted")
                           .setContentText(message)
                           .setSmallIcon(android.R.drawable.ic_notification_overlay)
                       if (ActivityCompat.checkSelfPermission(
                               applicationContext,
                               Manifest.permission.POST_NOTIFICATIONS
                           ) != PackageManager.PERMISSION_GRANTED
                       ) {
                           // TODO: Consider calling
                           //    ActivityCompat#requestPermissions
                           // here to request the missing permissions, and then overriding
                           //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                           //                                          int[] grantResults)
                           // to handle the case where the user grants the permission. See the documentation
                           // for ActivityCompat#requestPermissions for more details.
                           return@withContext
                       }
                       NotificationManagerCompat.from(applicationContext).notify(1, builder.build())
                   }
               }
            }
        }

    }
}