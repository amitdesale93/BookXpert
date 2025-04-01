package com.ap.pdfviewerappbookxpert.di

import android.content.Context
import androidx.room.Room
import com.ap.pdfviewerappbookxpert.R
import com.ap.pdfviewerappbookxpert.data.local.AppDatabase
import com.ap.pdfviewerappbookxpert.data.local.ItemDao
import com.ap.pdfviewerappbookxpert.data.local.UserDao
import com.ap.pdfviewerappbookxpert.data.remote.ApiService
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService =
        Retrofit.Builder()
            .baseUrl("https://api.restful-api.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_db").build()

/*    @Singleton
    @Provides
    fun provideAuthenticator() : BaseAuthenticator{
        return  FirebaseAuthenticator()
    }*/

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase) : UserDao = database.userDao()

    @Provides
    @Singleton
    fun provideItemDao(database: AppDatabase) : ItemDao = database.itemDao()


    @Provides
    @Singleton
    fun provideGoogleSignInClient(@ApplicationContext context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

}