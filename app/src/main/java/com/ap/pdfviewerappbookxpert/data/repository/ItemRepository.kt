package com.ap.pdfviewerappbookxpert.data.repository

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.ap.pdfviewerappbookxpert.PdfViewerBookExpertApplication
import com.ap.pdfviewerappbookxpert.R
import com.ap.pdfviewerappbookxpert.data.local.ItemDao
import com.ap.pdfviewerappbookxpert.data.remote.ApiService
import com.ap.pdfviewerappbookxpert.model.Item
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(private val apiService: ApiService, private val itemDao: ItemDao) {
    val items: LiveData<List<Item>> = itemDao.getItems()
    suspend fun fetchAndStoreItems() {
        val fetchedItems = apiService.fetchItems()
        itemDao.insertItems(fetchedItems)
    }
    suspend fun deleteItem(item: Item) {
        itemDao.deleteItem(item)
        sendNotification(item)
    }

    private fun sendNotification(item: Item) {
        val context = PdfViewerBookExpertApplication.instance.applicationContext
        val notificationManager = NotificationManagerCompat.from(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                return // Exit if permission is not granted
            }
        }

        val notification = NotificationCompat.Builder(context, "delete_channel")
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setContentTitle("Item Deleted")
            .setContentText("Deleted: ${item.name}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(1, notification)
    }}