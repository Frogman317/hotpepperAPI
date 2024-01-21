package com.kotlincocktail.hotpepperapi.tool

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class Haversine {
    fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val baseRadius = 6371 // 地球の半径（km）

        val latDistance = Math.toRadians(lat2 - lat1)
        val lonDistance = Math.toRadians(lon2 - lon1)
        val distance = sin(latDistance / 2) * sin(latDistance / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(lonDistance / 2) * sin(lonDistance / 2)
        val c = 2 * atan2(sqrt(distance), sqrt(1 - distance))

        return baseRadius * c
    }
}
