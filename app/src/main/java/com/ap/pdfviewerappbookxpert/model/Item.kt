package com.ap.pdfviewerappbookxpert.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
data class Item(
    @PrimaryKey val id: String,
    val name: String,
    @TypeConverters(DataConverter::class) val data: ItemData? = null // Nullable field
)