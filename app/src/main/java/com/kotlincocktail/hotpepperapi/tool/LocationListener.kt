package com.kotlincocktail.hotpepperapi.tool

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class LocationListener {
    fun getLocation(
        activity: Activity,
        context: Context,
        callback: (Location?) -> Unit
    ) {
        val client: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        client.lastLocation.addOnCompleteListener(activity) { task ->
            if (task.isSuccessful && task.result != null) {
                // Location found
                val location: Location = task.result!!
                callback(location)
            } else {
                // Error getting location
                callback(null)
            }
        }
    }
}