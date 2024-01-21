package com.kotlincocktail.hotpepperapi.tool

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HotpepperAPI{
    @GET("/hotpepper/gourmet/v1")
    suspend fun getGourmet(
        @Query("key") key: String = "40b28d2a29eb23a3",
        @Query("format") format: String = "json",
        @Query("type") type: String = "lite",
        @Query("keyword") keyword: String = "",
        @Query("lat") lat: String = "35.669220",
        @Query("lng") lng: String = "139.761457",
    ): Response<GourmetData>
    data class GourmetData(
        val results: Results
    )
    data class Results(
        val results_returned: String,
        val shop: List<Shop>,
    )
    data class Shop(
        val name: String,
        val address: String,
        val lat: Float,
        val lng: Float,
        val genre: Genre,
        val catch: String,
        val access: String,
        val urls: Urls,
        val photo: Photo
    )

    data class Genre(
        val name: String,
        val catch: String,
    )
    data class Urls(
        val pc: String
    )
    data class Photo(
        val pc: PhotoData
    )
    data class PhotoData(
        val l: String,
        val m: String,
        val s: String
    )
}