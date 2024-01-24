package com.kotlincocktail.hotpepperapi.view

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.kotlincocktail.hotpepperapi.tool.HotpepperAPI
import com.kotlincocktail.hotpepperapi.tool.LocationListener
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@SuppressLint("MissingPermission")
@Composable
fun SearchView(
    resultSet: (HotpepperAPI.GourmetResults?) -> Unit
) {
    val client = OkHttpClient.Builder().build()
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val api = Retrofit.Builder()
        .baseUrl("https://webservice.recruit.co.jp")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(HotpepperAPI::class.java)
    var genreCode: HotpepperAPI.GenreData? by remember { mutableStateOf(null) }
    LaunchedEffect(true) {
        withContext(Dispatchers.IO){
            genreCode = api.getGenre().body()
        }
    }
    val scrollState = rememberScrollState()
    val conditions = remember {
        mutableStateOf(
            mapOf(
                "wifi" to Pair("WiFi利用可能",false),
                "course" to Pair("コースあり",false),
                "free_drink" to Pair("飲み放題",false),
                "free_food" to Pair("食べ放題",false),
                "private_room" to Pair("個室あり",false),
                "horigotatsu" to Pair("掘りごたつあり",false),
                "tatami" to Pair("座敷あり",false),
                "cocktail" to Pair("カクテル充実",false),
                "shochu" to Pair("焼酎充実",false),
                "sake" to Pair("日本酒充実",false),
                "wine" to Pair("ワイン充実",false),
                "card" to Pair("カード可",false),
                "non_smoking" to Pair("禁煙席",false),
                "charter" to Pair("貸切可",false),
                "ktai" to Pair("携帯電話OK",false),
                "parking" to Pair("駐車場あり",false),
                "barrier_free" to Pair("バリアフリー",false),
                "sommelier" to Pair("ソムリエがいる",false),
                "night_view" to Pair("夜景がキレイ",false),
                "open_air" to Pair("オープンエア",false),
                "show" to Pair("ライブ・ショーあり",false),
                "equipment" to Pair("エンタメ設備",false),
                "karaoke" to Pair("カラオケあり",false),
                "band" to Pair("バンド演奏可",false),
                "tv" to Pair("TV・プロジェクター",false),
                "lunch" to Pair("ランチあり",false),
                "midnight" to Pair("23時以降も営業",false),
                "midnight_meal" to Pair("23時以降食事OK",false),
                "english" to Pair("英語メニューあり",false),
                "pet" to Pair("ペット可",false),
                "child" to Pair("お子様連れOK",false),
            )
        )
    }
    var lat: Double
    var lon: Double
    val context = LocalContext.current
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val locationListener = LocationListener()

    var keyword: String? by remember { mutableStateOf(null) }
    var isExpanded by remember { mutableStateOf(false) }
    var genre: HotpepperAPI.GenreCode? by remember { mutableStateOf(null) }
    var slider by remember { mutableFloatStateOf(10f) }
    val sliderInt = slider.toInt()
    var selectedIndex by remember { mutableStateOf(2) }
    val options = listOf("300m", "500m", "1000m","2000m","3000m")

    val scope = rememberCoroutineScope()
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.scrollable(scrollState,Orientation.Vertical)
    ) {
        item {
            Text(
                text = "グルメサーチAPI",
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
        item {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 8.dp),
                label = { Text(text = "キーワード検索") },
                value = keyword ?: "",
                onValueChange = { keyword = if (it == "") null else it }
            )
        }
        item {
            Column {
                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = { isExpanded = it }
                ) {
                    TextField(
                        label = { Text(text = "ジャンルの選択")},
                        value = genre?.name ?: "",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                        },
                        placeholder = {
                            Text(text = "選択してください")
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(0.8f)
                    )
                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = {
                            isExpanded = false
                        }
                    ) {
                        genreCode?.results?.genre?.forEach { data ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = data.name)
                                },
                                onClick = {
                                    genre = data
                                    isExpanded = false
                                }
                            )
                        }
                    }
                }
            }

        }
        item {
            Text(text = "検索範囲")
        }
        item {
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                options.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                        onClick = { selectedIndex = index },
                        selected = index == selectedIndex
                    ) {
                        Text(
                            text = label,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
        item {
            Text(text = "検索件数:  $sliderInt 件")
            Slider(
                modifier = Modifier.fillMaxWidth(0.8f),
                value = slider,
                onValueChange = {slider =it},
                steps = 48,
                valueRange = 1f..50f
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                for ((key, value) in conditions.value) {
                    FilterChip(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        selected = value.second,
                        onClick = { conditions.value = conditions.value.toMutableMap().apply { this[key] = Pair(value.first,!value.second) } },
                        label = { Text(text = value.first) }
                    )
                }
            }
        }
        item {
            Button(
                modifier = Modifier.fillMaxWidth(0.8f),
                onClick = {
                    var data: HotpepperAPI.GourmetResults?
                    if (permissionState.hasPermission) {
                        locationListener.getLocation(permissionState,context){ location ->
                            location?.let {
                                lat = it.latitude
                                lon = it.longitude
                                scope.launch {
                                    withContext(Dispatchers.IO) {
                                        val response = api.getGourmet(
                                            lat = lat.toString(),
                                            lng = lon.toString(),
                                            range = (selectedIndex+1).toString(),
                                            genre = genre?.code,
                                            count = sliderInt.toString(),
                                            keyword = keyword,
                                            wifi = if(conditions.value["wifi"]?.second == true) "1" else null,
                                            course = if(conditions.value["course"]?.second == true) "1" else null,
                                            freeDrink = if(conditions.value["free_drink"]?.second == true) "1" else null,
                                            freeFood = if(conditions.value["free_food"]?.second == true) "1" else null,
                                            privateRoom = if(conditions.value["private_room"]?.second == true) "1" else null,
                                            horigotatsu = if(conditions.value["horigotatsu"]?.second == true) "1" else null,
                                            tatami = if(conditions.value["tatami"]?.second == true) "1" else null,
                                            cocktail = if(conditions.value["cocktail"]?.second == true) "1" else null,
                                            shochu = if(conditions.value["shochu"]?.second == true) "1" else null,
                                            sake = if(conditions.value["sake"]?.second == true) "1" else null,
                                            wine = if(conditions.value["wine"]?.second == true) "1" else null,
                                            card = if(conditions.value["card"]?.second == true) "1" else null,
                                            nonSmoking = if(conditions.value["nonSmoking"]?.second == true) "1" else null,
                                            charter = if(conditions.value["charter"]?.second == true) "1" else null,
                                            ktai = if(conditions.value["ktai"]?.second == true) "1" else null,
                                            parking = if(conditions.value["parking"]?.second == true) "1" else null,
                                            barrierFree = if(conditions.value["barrierFree"]?.second == true) "1" else null,
                                            sommelier = if(conditions.value["sommelier"]?.second == true) "1" else null,
                                            nightView = if(conditions.value["nightView"]?.second == true) "1" else null,
                                            openAir = if(conditions.value["openAir"]?.second == true) "1" else null,
                                            show = if(conditions.value["show"]?.second == true) "1" else null,
                                            equipment = if(conditions.value["equipment"]?.second == true) "1" else null,
                                            karaoke = if(conditions.value["karaoke"]?.second == true) "1" else null,
                                            band = if(conditions.value["band"]?.second == true) "1" else null,
                                            tv = if(conditions.value["tv"]?.second == true) "1" else null,
                                            lunch = if(conditions.value["lunch"]?.second == true) "1" else null,
                                            midnight = if(conditions.value["midnight"]?.second == true) "1" else null,
                                            midnightMeal = if(conditions.value["midnightMeal"]?.second == true) "1" else null,
                                            english = if(conditions.value["english"]?.second == true) "1" else null,
                                            pet = if(conditions.value["pet"]?.second == true) "1" else null,
                                            child = if(conditions.value["child"]?.second == true) "1" else null,
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
                    } else {
                        permissionState.launchPermissionRequest()
                    }
                }
            ) {
                Text(text = "検索")
            }
        }



    }
}