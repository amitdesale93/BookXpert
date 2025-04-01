package com.ap.pdfviewerappbookxpert.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class DataConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromItemData(value: ItemData?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toItemData(value: String?): ItemData? {
        return gson.fromJson(value, ItemData::class.java)
    }
}