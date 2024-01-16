package com.kotlincocktail.hotpepperapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kotlincocktail.hotpepperapi.tool.RequestLocationPermission
import com.kotlincocktail.hotpepperapi.ui.theme.HotpepperAPITheme
import com.kotlincocktail.hotpepperapi.view.SearchView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RequestLocationPermission()
            HotpepperAPITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SearchView()
                }
            }
        }
    }
}