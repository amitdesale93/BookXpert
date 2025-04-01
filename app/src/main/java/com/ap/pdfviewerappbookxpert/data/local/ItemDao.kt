package com.ap.pdfviewerappbookxpert.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ap.pdfviewerappbookxpert.model.Item

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<Item>)

    @Query("SELECT * FROM Item")
    fun getItems(): LiveData<List<Item>>

    @Delete
    suspend fun deleteItem(item: Item)
}