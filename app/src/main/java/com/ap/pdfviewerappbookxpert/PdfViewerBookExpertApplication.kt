package com.ap.pdfviewerappbookxpert

import android.app.Application
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import com.ap.pdfviewerappbookxpert.utils.DataStoreManager
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PdfViewerBookExpertApplication : Application() {

    override fun onCreate() {
        super.onCreate()


        FirebaseApp.initializeApp(applicationContext)
            DataStoreManager.applyTheme(this@PdfViewerBookExpertApplication)
    }
}
