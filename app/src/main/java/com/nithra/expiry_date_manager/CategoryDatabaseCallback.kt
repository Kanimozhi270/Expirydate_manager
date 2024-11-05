package com.nithra.expiry_date_manager

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CategoryDatabaseCallback(
    private val scope: CoroutineScope
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        scope.launch {
           // populateDatabase(AppDatabase.getDatabase(applicationContext).categoryDao())
        }
    }

    suspend fun populateDatabase(categoryDao: CategoryDao) {
        // Insert default categories
        val categories = listOf(
            Category(name = "Food"),
            Category(name = "Bills"),
            Category(name = "Groceries"),
            Category(name = "Subscriptions"),
            Category(name = "Medicine")
        )
        categories.forEach { categoryDao.insert(it) }
    }
}
