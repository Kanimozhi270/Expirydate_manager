package com.nithra.expiry_date_manager

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ItemDao {
    @Insert
    suspend fun insert(item: com.nithra.expiry_date_manager.ItemViewModel.Item)

    @Query("SELECT * FROM item_table")
    suspend fun getAllItems(): List<Item>
}
