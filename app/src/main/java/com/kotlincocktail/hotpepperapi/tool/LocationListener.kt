package com.kotlincocktail.hotpepperapi.tool

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.android.gms.location.LocationServices


class LocationListener {
    @OptIn(ExperimentalPermissionsApi::class)
    fun getLocation(
        permissionState: PermissionState,
        context: Context,
        callback: (Location?) -> Unit
    ) {
        if (permissionState.hasPermission) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val lat = location.latitude
                        val lon = location.longitude
                        Log.d("TAG", "lat: $lat lon: $lon")
                        callback(location)
                    } else {
                        // Error getting location
                    }
                }
            }
        } else {
            permissionState.launchPermissionRequest()
        }
    }
}