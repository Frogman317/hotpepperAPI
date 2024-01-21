package com.kotlincocktail.hotpepperapi.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.kotlincocktail.hotpepperapi.tool.HotpepperAPI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultView(
    navController: NavHostController,
    data: HotpepperAPI.Results?,
    selectedData: (HotpepperAPI.Shop) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { /*TODO*/ },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = "")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues)
        ) {
            LazyColumn{
                data?.results_returned?.let { count ->
                    items(count = count.toInt()){index ->
                        ListContent(
                            shop = data.shop[index]
                        ) {
                            selectedData(it)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListContent(
    shop: HotpepperAPI.Shop,
    selectedData: (HotpepperAPI.Shop) -> Unit
) {
    Log.d("shop data", shop.toString())
    ListItem(
        modifier = Modifier
            .clickable{
                selectedData(shop)
            },
        headlineText = {
            Text(text = shop.name)
        },
        supportingText = {
           Text(text = shop.access)
        },
        leadingContent = {
            AsyncImage(
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp),
                model = shop.photo.pc.l,
                contentDescription = "shop image contains"
            )
        }
    )
}