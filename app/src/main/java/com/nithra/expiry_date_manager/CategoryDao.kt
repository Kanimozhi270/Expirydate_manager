package com.nithra.expiry_date_manager

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao {
    @Insert
    suspend fun insert(category: Category)

    @Query("SELECT * FROM category_table")
    suspend fun getAllCategories(): List<Category>

    @Insert
    suspend fun insertDefaultCategories() {
        val defaultCategories = listOf(
            Category(name = "Groceries"),
            Category(name = "Medicine"),
            Category(name = "Personal Care"),
            Category(name = "Electronics"),
            Category(name = "Others")
        )
        insertAll(defaultCategories)
    }

    @Insert
    suspend fun insertAll(categories: List<Category>)
}
