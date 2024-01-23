package com.kotlincocktail.hotpepperapi.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kotlincocktail.hotpepperapi.tool.HotpepperAPI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(
    navController: NavController,
    data: HotpepperAPI.Shop
) {
    Scaffold(
        topBar = { TopAppBar(
            title = { Text(text = data.name) },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = ""
                    )
                }
            }
        )}
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "ジャンル: ${data.genre.name}"
            )
            Text(
                text = "キャッチ: ${data.catch}"
            )
            Text(
                text = "営業時間: ${data.open}"
            )
            Text(
                text = "住所: ${data.address}"
            )
            Text(
                text = "アクセス: ${data.access}"
            )
            Text(
                text = "ホームページ: ${data.urls.pc}"
            )
            AsyncImage(
                model = data.photo.pc.l,
                contentDescription = ""
            )
        }
    }
}