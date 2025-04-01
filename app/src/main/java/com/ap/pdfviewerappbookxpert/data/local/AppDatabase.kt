package com.ap.pdfviewerappbookxpert.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ap.pdfviewerappbookxpert.model.DataConverter
import com.ap.pdfviewerappbookxpert.model.Item
import com.ap.pdfviewerappbookxpert.model.User

@Database(entities = [User::class,Item::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class) // Register the TypeConverter

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun itemDao(): ItemDao
}