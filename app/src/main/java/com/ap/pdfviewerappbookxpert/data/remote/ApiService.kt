package com.ap.pdfviewerappbookxpert.data.remote

import com.ap.pdfviewerappbookxpert.model.Item
import com.ap.pdfviewerappbookxpert.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("objects")
    suspend fun getUsers(): List<User>

    @GET("objects")
    suspend fun fetchItems(): List<Item>
}
