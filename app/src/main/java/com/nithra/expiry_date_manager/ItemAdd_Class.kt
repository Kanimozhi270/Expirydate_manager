package com.nithra.expiry_date_manager

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import java.util.Calendar

class ItemAdd_Class : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create or retrieve the ViewModel instance
        val viewModel = ViewModelProvider(this)[ItemViewModel::class.java]

        setContent {
            CreateItemScreen(viewModel)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateItemScreen(viewModel: ItemViewModel) {
    var itemName by remember { mutableStateOf("") }
    var itemType by remember { mutableStateOf("Expiry") }  // Default to Expiry Item
    var itemCategory by remember { mutableStateOf("Select a Category") }
    var reminderBefore by remember { mutableStateOf("Same Day") }
    var expiryDate by remember { mutableStateOf("") }
    var notifyTime by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var imagePath by remember { mutableStateOf<String>("") }

    val calendar = Calendar.getInstance()
    val scrollState = rememberScrollState()

    val context = LocalContext.current // Get the current context for Toast


    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imagePath = uri.toString()
    }


    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            expiryDate = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        LocalContext.current,
        { _, hourOfDay, minute ->
            notifyTime = String.format("%02d:%02d", hourOfDay, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(start = 12.dp, end = 12.dp)

    ) {
        TopAppBar(
            title = { Text("Create item", color = Color.Black) },
            navigationIcon = {
                IconButton(onClick = { /* Handle back action */ }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color.Black
            )
        )
          //item name
        Column(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
            Text(
                text = "Item Name",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            OutlinedTextField(
                value = itemName,
                onValueChange = {
                    if (it.length <= 30) {
                        itemName = it
                    }
                },
                label = null,
                placeholder = { Text(text = "Item Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))


        // Item Type (Expiry or Renew)
        Text(
            text = "Item Type",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 5.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { itemType = "Expiry" }, // Set selected type to "Expiry" when clicked
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (itemType == "Expiry") Color(0xFF9C6EFF) else Color.LightGray // Purple if selected, gray otherwise
                )
            ) {
                Text(
                    text = "Expiry Item",
                    color = if (itemType == "Expiry") Color.White else Color.Black // White text when selected, black otherwise
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { itemType = "Renew" }, // Set selected type to "Renew" when clicked
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (itemType == "Renew") Color(0xFF9C6EFF) else Color.LightGray // Purple if selected, gray otherwise
                )
            ) {
                Text(
                    text = "Renew Item",
                    color = if (itemType == "Renew") Color.White else Color.Black // White text when selected, black otherwise
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        // Category
        Column(modifier = Modifier.fillMaxWidth()) {
            // Label
            Text(
                text = "Category",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            // TextField with icon
            OutlinedTextField(
                value = itemCategory,
                onValueChange = { itemCategory = it },
                label = null, // Label can be outside or on the field itself
                placeholder = { Text(text = "Select Category") }, // Placeholder for the field
                trailingIcon = {
                    IconButton(onClick = { /* Show Date Picker */ }) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Select Date",
                            tint = Color.Gray
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // If you want number input
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        // Add Expiry date
        Column(modifier = Modifier.fillMaxWidth()) {
            // Label
            Text(
                text = "Add Expiry date",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            // TextField with icon
            OutlinedTextField(
                value = expiryDate,
                onValueChange = { expiryDate = it },
                label = null,
                placeholder = { Text(text = "Select date") },
                trailingIcon = {
                    IconButton(onClick = { datePickerDialog.show()}) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select Date",
                            tint = Color.Gray
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), // If you want number input
                readOnly = true,
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        // Reminder Before Options
        Text(
            text = "Reminder Before",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 5.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ReminderButton("Same Day", reminderBefore) { reminderBefore = it }
            ReminderButton("2 Days", reminderBefore) { reminderBefore = it }
            ReminderButton("1 Week", reminderBefore) { reminderBefore = it }
        }

        Spacer(modifier = Modifier.height(10.dp))
        //notify time
        Column(modifier = Modifier.fillMaxWidth()) {
            // Label
            Text(
                text = "Notify Time",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            // TextField with icon
            OutlinedTextField(
                value = notifyTime,
                onValueChange = { notifyTime = it },
                label = null, // Label can be outside or on the field itself
                placeholder = { Text(text = "Notify Time") }, // Placeholder for the field
                trailingIcon = {
                    IconButton(onClick = { timePickerDialog.show() })  {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select Date",
                            tint = Color.Gray
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), // If you want number input
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable { timePickerDialog.show() },
                readOnly = true
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        // Add Note
        Column(modifier = Modifier.fillMaxWidth()) {
            // Label
            Text(
                text = "Add Note",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            // TextField with icon
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = null, // Label can be outside or on the field itself
                placeholder = { Text(text = "Add Notes") }, // Placeholder for the field
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), // If you want number input
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        // Image Upload Placeholder
        Text(
            text = "Image",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 5.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .background(Color(0xFFF5F5F5))
                .clickable { imageLauncher.launch("image/*") }, // Open gallery on click
            contentAlignment = Alignment.Center
        ) {
            if (imagePath.isNotEmpty()) {
                AsyncImage(
                    model = imagePath,
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { imageLauncher.launch("image/*") }) {
                        Icon(Icons.Default.Add, contentDescription = "Upload Image")
                    }
                    Text(text = "Upload Image")
                }
            }
        }


        Spacer(modifier = Modifier.height(10.dp))

        // Add Item Button
        Button(
            onClick = {
                // Check for each field separately and show specific Toast messages
                when {
                    itemName.isBlank() -> {
                        Toast.makeText(context, "Please fill in the Item Name", Toast.LENGTH_SHORT).show()
                    }
                    itemType.isBlank() -> {
                        Toast.makeText(context, "Please fill in the Item Type", Toast.LENGTH_SHORT).show()
                    }
                    itemCategory == "Select a Category" -> {
                        Toast.makeText(context, "Please select a Category", Toast.LENGTH_SHORT).show()
                    }
                    expiryDate.isBlank() -> {
                        Toast.makeText(context, "Please enter the Expiry Date", Toast.LENGTH_SHORT).show()
                    }
                    reminderBefore.isBlank() -> {
                        Toast.makeText(context, "Please select a Reminder Before", Toast.LENGTH_SHORT).show()
                    }
                    notifyTime.isBlank() -> {
                        Toast.makeText(context, "Please set a Notify Time", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        // Use the ViewModel to insert item data into the database
                        viewModel.insertItem(
                            id = 0,
                            itemName = itemName,
                            itemType = itemType,
                            itemCategory = itemCategory,
                            itemExpirydate = expiryDate,
                            reminderBefore = reminderBefore,
                            notifyTime = notifyTime,
                            imagePath = imagePath,
                            itemNotes = notes
                        )

                        // Show a Toast message after successful insertion
                        Toast.makeText(context, "Item Added Successfully", Toast.LENGTH_SHORT).show()

                        // Clear the form after adding the item
                        itemName = ""
                        itemType = "Expiry Item"
                        itemCategory = "Select a Category"
                        reminderBefore = "Same Day"
                        expiryDate = ""
                        notifyTime = ""
                        notes = ""
                        imagePath = ""
                    }
                }
            }
        ) {
            Text(text = "Add Item")
        }

    }
}
@Composable
fun ReminderButton(text: String, selectedReminder: String, onClick: (String) -> Unit) {
    Button(
        onClick = { onClick(text) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (text == selectedReminder) Color(0xFF9C6EFF) else Color.LightGray
        )
    ) {
        Text(
            text = text,
            color = if (text == selectedReminder) Color.White else Color.Black
        )
    }
}