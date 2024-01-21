package com.kotlincocktail.hotpepperapi.view

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.kotlincocktail.hotpepperapi.tool.HotpepperAPI
import com.kotlincocktail.hotpepperapi.tool.LocationListener
import com.kotlincocktail.hotpepperapi.tool.RequestPermission
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@SuppressLint("MissingPermission")
@Composable
fun SearchView(
    navController: NavHostController,
    resultSet: (HotpepperAPI.Results?) -> Unit
) {
    val client = OkHttpClient.Builder().build()
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val api = Retrofit.Builder()
        .baseUrl("https://webservice.recruit.co.jp")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(HotpepperAPI::class.java)

    var lat: Double
    var lon: Double
    val activity = LocalContext.current as Activity
    val context = LocalContext.current


    //TODO 検索条件項目作成




    val scope = rememberCoroutineScope()
    Button(onClick = {
        var data: HotpepperAPI.Results?
        LocationListener().getLocation(activity,context) { location ->
            location?.let {
                lat = it.latitude
                lon = it.longitude
                Log.d("TAG", "lat: $lat lon: $lon")
                scope.launch {
                    withContext(Dispatchers.IO) {
                        val response = api.getGourmet(
                            lat = lat.toString(),
                            lng = lon.toString(),
                        )
                        if (response.isSuccessful) {
                            data = response.body()?.results
                            withContext(Dispatchers.Main) {
                                resultSet(data)
                            }
                        } else {
                            //TODO 取得失敗
                        }
                    }
                }
            }
        }
    }) {
        Text(text = "検索")
    }
}