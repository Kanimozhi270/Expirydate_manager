package com.nithra.expiry_date_manager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val itemName: String,
    val itemType: String,  // "Expiry Item" or "Renew Item"
    val itemCategory: String,
    val ItemExpirydate: String,
    val reminderBefore: String,
    val notifyTime: String,
    val ItemNotes: String,
    val imagePath: String?  // Path to the image if available
)

