package com.nithra.expiry_date_manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nithra.expiry_date_manager.ui.theme.Expiry_date_managerTheme
import kotlinx.coroutines.launch

class category_class : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Expiry_date_managerTheme {
                CategoriesScreen()
            }
        }
    }

    @Composable
    fun CategoriesScreen() {
        // Remember selected tab state
        val selectedTabIndex = remember { mutableStateOf(0) }
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Top Navigation
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Categories",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // TabRow for Expiry and Renew Items
            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                Tab(
                    selected = selectedTabIndex.value == 0,
                    onClick = {
                        coroutineScope.launch { selectedTabIndex.value = 0 }
                    },
                    text = { Text(text = "Expiry Item") }
                )
                Tab(
                    selected = selectedTabIndex.value == 1,
                    onClick = {
                        coroutineScope.launch { selectedTabIndex.value = 1 }
                    },
                    text = { Text(text = "Renew Item") }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Show content based on selected tab
            when (selectedTabIndex.value) {
                0 -> ExpiryItemContent()
                1 -> RenewItemContent()
            }
        }
    }

    @Composable
    fun ExpiryItemContent() {
        // Content for Expiry Items (Can be a list of expiry items)
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(categoryList.size) { index ->
                CategoryItem(categoryList[index])
            }
        }
    }

    @Composable
    fun RenewItemContent() {
        // Content for Renew Items (Can be a list of renew items)
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(categoryList.size) { index ->
                CategoryItem(categoryList[index])
            }
        }
    }

    @Composable
    fun CategoryItem(category: Category) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable { /* Handle click */ },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = category.icon),  // Ensure this icon exists
                    contentDescription = category.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(48.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = category.name, fontSize = 14.sp)
        }
    }

    // Updated data class with unique name to avoid conflict with java.util.Locale.Category
    data class Category(val name: String, val icon: Int)

    val categoryList = listOf(
        Category("Govt Docs", R.drawable.ladysfinger),
        Category("School", R.drawable.carrot),
        Category("College", R.drawable.television),
        Category("Bills", R.drawable.tomato),
        Category("OTT", R.drawable.ladysfinger),
        Category("Insurance", R.drawable.carrot),
        Category("Vehicle", R.drawable.tomato),
        Category("Electronics", R.drawable.ladysfinger)
    )

    @Preview
    @Composable
    fun PreviewCategoriesScreen() {
        CategoriesScreen()
    }
}
