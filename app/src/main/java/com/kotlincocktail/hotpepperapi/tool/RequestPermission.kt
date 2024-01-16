package com.kotlincocktail.hotpepperapi.tool

import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission(hasPermission:  () -> Unit = {}) {
    val permissionState = rememberPermissionState(ACCESS_FINE_LOCATION)
    when {
        permissionState.hasPermission -> hasPermission
        permissionState.shouldShowRationale -> SideEffect {
            //TODO リクエストを要求する理由
            permissionState.launchPermissionRequest()
        }
        permissionState.permissionRequested -> {}
        else -> SideEffect {
            permissionState.launchPermissionRequest()
        }
    }
}