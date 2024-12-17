package com.nithra.expiry_date_manager

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nithra.expiry_date_manager.ui.theme.Expiry_date_managerTheme

class MainActivity : ComponentActivity() {
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Expiry_date_managerTheme {
                DateManagerScreen()
            }
        }
        database = AppDatabase.getDatabase(this)

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DateManagerScreen() {
        // Add a Scroll State for vertical scrolling
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState) // Enable vertical scrolling
                .background(Color.White)
        ) {
            // Top App Bar with back arrow and settings icon
            TopAppBar(
                title = { Text("Expiry Date Manager", color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back action */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val intent = Intent(this@MainActivity, category_class::class.java)
                        startActivity(intent)
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.category), // Your custom icon here
                            contentDescription = "Settings",
                            modifier = Modifier.size(24.dp) // Adjust size as needed
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )

            GreetingCard()


            // Item section - To be expired in 7 days
            Text(
                text = "Item to be expired in 7 days",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold // Make the text bold
                ),
                modifier = Modifier.padding(10.dp)
            )
            ExpiringItemsSection()

            // Item section - To be renewed in 7 days
            Text(
                text = "Item to be renewed in 7 days",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold // Make the text bold
                ),
                modifier = Modifier.padding(10.dp)
            )
            RenewingItemsSection()

            // Recently Added Items section
            Text(
                text = "Recently added items",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold // Make the text bold
                ),
                modifier = Modifier.padding(10.dp)
            )
            RecentlyAddedItemsSection()

            Spacer(modifier = Modifier.height(10.dp))

            // Add Item Button
            Button(
                onClick = {
                    val intent = Intent(this@MainActivity, ItemAdd_Class::class.java)
                    startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B73F6)), // Use containerColor instead of backgroundColor
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "+ Add Item", color = Color.White)
            }
        }
    }

    @Composable
    fun GreetingCard() {
        // Gradient background for the card
        val gradientColors = listOf(
            Color(0xFF8E2DE2), // Purple
            Color(0xFF4A00E0)  // Darker Purple
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
                .background(
                    brush = Brush.horizontalGradient(gradientColors),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp) // Inner padding for content
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Welcome !!",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Kanimozhi Vanjinathan",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
               // SearchBar()
            }
        }
    }

    @Composable
    fun SearchBar() {
        val searchQuery = remember { mutableStateOf(TextFieldValue("")) }

        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            placeholder = {
                Text(
                    text = "Search Your Item",
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp), // Rounded corners for the search bar
            singleLine = true
        )
    }

    @Composable
    fun ExpiringItemsSection() {
        val itemsList = listOf("Ladies Finger", "Carrot", "Tomato","beetroot","cabbage","onion","capsicum")

        LazyRow(modifier = Modifier.padding(horizontal = 10.dp)) {
            items(itemsList) { item -> // Correct way to pass the list to the items function
                ExpiringItemCard(item = item, Category = "Vegtables")
            }
        }
    }

    @Composable
    fun ExpiringItemCard(item: String, Category: String) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(4.dp) // Reduced padding
                .width(120.dp) // Reduced width
        ) {
            Column(
                modifier = Modifier.padding(2.dp), // Reduced padding inside the card
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ladysfinger), // Placeholder image
                    contentDescription = item,
                    modifier = Modifier
                        .size(80.dp) // Reduced image size
                        .clip(RoundedCornerShape(8.dp))
                )
                Text(text = item, style = MaterialTheme.typography.bodySmall) // Smaller text style
                Text(text = Category, color = Color.Black, style = MaterialTheme.typography.bodySmall) // Smaller text style
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFFFFCDD2),  // Light Red
                                    Color(0xFFFFFFFF)   // Darker Red
                                )
                            ), shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "3hrs Remaining ▼",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }


    @Composable
    fun RenewingItemsSection() {
        LazyRow(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(listOf("Current Bills", "Passport", "Hotstar", "Netflix", "Amazon Prime", "Spotify", "Youtube")) { item ->
                RenewingItemCard(item = item.toString(), Category = "Category")
            }
        }
    }

    @Composable
    fun RenewingItemCard(item: String, Category:String) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(4.dp)
                .width(110.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.television), // Placeholder image
                    contentDescription = item,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = item, style = MaterialTheme.typography.bodyMedium)
                Text(text = Category, color = Color.Black)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF0AA34F),  // Light Red
                                    Color(0xFFFFFFFF)   // Darker Red
                                )
                            ), shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "3day Remaining ▼",
                        color = Color(0xFF0AA34F),
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }

    @Composable
    fun RecentlyAddedItemsSection() {
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            val items = listOf(
                "Ladies Finger" to "2 days Remaining",
                "Banana" to "5 days Remaining",
                "Chicken" to "5 days Remaining",
                "Ladies Finger" to "2 days Remaining",
                "Banana" to "5 days Remaining",
            )
            items.forEach { (item, remaining) ->
                RecentlyAddedItemCard(item = item, remaining = remaining)
            }
        }
    }

    @Composable
    fun RecentlyAddedItemCard(item: String, remaining: String) {
        Card(
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tomato), // Placeholder image
                    contentDescription = item,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = item, style = MaterialTheme.typography.headlineMedium, fontSize = 18.sp)
                    Text(text = remaining, color = Color.Green)
                }
                Row {
                    IconButton(onClick = { /* Edit Action */ }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = { /* Remove Action */ }) {
                        Icon(Icons.Default.Delete, contentDescription = "Remove")
                    }
                }
            }
        }

}}




