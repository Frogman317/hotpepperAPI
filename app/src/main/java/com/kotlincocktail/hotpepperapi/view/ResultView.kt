package com.kotlincocktail.hotpepperapi.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.kotlincocktail.hotpepperapi.tool.HotpepperAPI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultView(
    navController: NavHostController,
    data: HotpepperAPI.GourmetResults?,
    selectedData: (HotpepperAPI.Shop) -> Unit
) {
    Scaffold(
        topBar = {
            val count = data?.results_returned ?: "0"
            TopAppBar(
                title = { Text(text = "検索結果: $count 件") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        var pageNum by remember { mutableIntStateOf(0) }
        val start = pageNum * 10
        val end = minOf(start + 10, data?.shop?.size ?: 0)
        LazyColumn(
            Modifier.padding(paddingValues)
        ) {
            data?.let { result ->
                itemsIndexed(result.shop.subList(start, end)) { index, shop ->
                    ListContent(shop = shop) {
                        selectedData(it)
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if (pageNum > 0){
                        Button(onClick = { pageNum -= 1 }) {
                            Text(text = "前のページに戻る")
                        }
                    }
                    if (end != (data?.shop?.size ?: 0)){
                        Button(onClick = { pageNum += 1 }) {
                            Text(text = "次のページに進む")
                        }
                    }
                }
            }
        }
    }
}

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
        headlineContent = {
            Text(text = shop.name)
        },
        supportingContent = {
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