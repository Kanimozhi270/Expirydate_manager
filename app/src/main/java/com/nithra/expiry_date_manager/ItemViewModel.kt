package com.nithra.expiry_date_manager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemViewModel(application: Application) : AndroidViewModel(application) {

    private val itemDao = AppDatabase.getDatabase(application).itemDao() // Assuming you have itemDao
    private val categoryDao = AppDatabase.getDatabase(application).categoryDao()

    private val _recentItems = MutableLiveData<List<Item>>()
    val recentItems: LiveData<List<Item>> = _recentItems

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    init {
        getAllCategories()
    }

    // Updated Item data class to match the parameters in insertItem

    data class Item(
        val id: Int,
        val itemName: String,
        val itemType: String,
        val itemCategory: String,
        val itemExpirydate: String,
        val reminderBefore: String,
        val notifyTime: String,
        val imagePath: String,
        val itemNotes: String
    )

    fun insertItem(
        id: Int,
        itemName: String,
        itemType: String,
        itemCategory: String,
        itemExpirydate: String,
        reminderBefore: String,
        notifyTime: String,
        imagePath: String,
        itemNotes: String
    ): Boolean {
        val newItem = Item(
            id = id,
            itemName = itemName,
            itemType = itemType,
            itemCategory = itemCategory,
            itemExpirydate = itemExpirydate,
            reminderBefore = reminderBefore,
            notifyTime = notifyTime,
            imagePath = imagePath,
            itemNotes = itemNotes
        )

        var isSuccess = false

        viewModelScope.launch(Dispatchers.IO) {
            try {
                itemDao.insert(newItem) // Uncomment and ensure this DAO method exists
                isSuccess = true
            } catch (e: Exception) {
                e.printStackTrace()
                isSuccess = false
            }
        }

        return isSuccess
    }

    private fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val categoriesList = categoryDao.getAllCategories()
            _categories.postValue(categoriesList)
        }
    }
}
