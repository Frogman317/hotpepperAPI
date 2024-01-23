package com.kotlincocktail.hotpepperapi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kotlincocktail.hotpepperapi.tool.HotpepperAPI
import com.kotlincocktail.hotpepperapi.view.DetailView
import com.kotlincocktail.hotpepperapi.view.ResultView
import com.kotlincocktail.hotpepperapi.view.SearchView

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "search"
) {
    var result: HotpepperAPI.GourmetResults? = null
    var selectedData: HotpepperAPI.Shop? = null
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = "search") {
            SearchView{
                result = it
                navController.navigate("result")
            }
        }
        composable(route = "result") {
            ResultView(
                navController = navController,
                data = result
            ){
                selectedData = it
                navController.navigate("detail")
            }
        }
        composable(route = "detail") {
            selectedData?.let { it1 ->
                DetailView(
                    navController = navController,
                    data = it1
                )
            }
        }
    }
}