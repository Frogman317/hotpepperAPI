package com.kotlincocktail.hotpepperapi.view

import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextDecoration
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
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                model = data.photo.pc.l,
                contentDescription = ""
            )
            Text(
                text = "ジャンル: ${data.genre.name}",
                modifier = Modifier
                    .padding(4.dp),
                fontSize = 20.sp
            )
            HorizontalDivider()
            Text(
                text = "キャッチ: ${data.catch}",
                modifier = Modifier
                    .padding(4.dp),
                fontSize = 20.sp
            )
            HorizontalDivider()
            Text(
                text = "営業時間: ${data.open}",
                modifier = Modifier
                    .padding(4.dp),
                fontSize = 20.sp
            )
            HorizontalDivider()
            Text(
                text = "住所: ${data.address}",
                modifier = Modifier
                    .padding(4.dp),
                fontSize = 20.sp
            )
            HorizontalDivider()
            Text(
                text = "アクセス: ${data.access}",
                modifier = Modifier
                    .padding(4.dp),
                fontSize = 20.sp
            )
            HorizontalDivider()
            Text(
                text = "ホームページ:",
                fontSize = 20.sp,
            )
            val urlHandler = LocalUriHandler.current
            Text(
                text = data.urls.pc,
                modifier = Modifier
                    .padding(4.dp)
                    .clickable {
                           urlHandler.openUri(data.urls.pc)
                    },
                fontSize = 20.sp,
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}