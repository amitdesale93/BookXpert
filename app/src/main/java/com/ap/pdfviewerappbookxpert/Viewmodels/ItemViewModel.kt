package com.ap.pdfviewerappbookxpert.Viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ap.pdfviewerappbookxpert.data.repository.ItemRepository
import com.ap.pdfviewerappbookxpert.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(private val repository: ItemRepository) : ViewModel() {
    val items: LiveData<List<Item>> = repository.items
    fun refreshItems() = viewModelScope.launch {
        repository.fetchAndStoreItems()
    }
    fun deleteItem(item: Item) = viewModelScope.launch {
        repository.deleteItem(item)
    }
}