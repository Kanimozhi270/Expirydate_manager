package com.nithra.expiry_date_manager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var itemName: String,
    var itemType: String,  // "Expiry Item" or "Renew Item"
    var itemCategory: String,
    var itemExpirydate: String,
    var reminderBefore: String,
    var notifyTime: String,
    var ItemNotes: String,
    var imagePath: String  // Path to the image if available
)

