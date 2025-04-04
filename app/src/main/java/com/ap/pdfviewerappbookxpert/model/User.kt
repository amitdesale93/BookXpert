package com.ap.pdfviewerappbookxpert.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey val id: String,
    val name: String?,
    val email: String?
)