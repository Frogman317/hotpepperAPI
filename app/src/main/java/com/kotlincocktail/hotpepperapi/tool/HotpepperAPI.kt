package com.kotlincocktail.hotpepperapi.tool

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HotpepperAPI{
    @GET("/hotpepper/genre/v1")
    suspend fun getGenre(
        @Query("key") key: String = "40b28d2a29eb23a3",
        @Query("format") format: String = "json",

    ): Response<GenreData>
    @GET("/hotpepper/gourmet/v1")
    suspend fun getGourmet(
        @Query("key") key: String = "40b28d2a29eb23a3",
        @Query("format") format: String = "json",
        @Query("keyword") keyword: String? = null,
        @Query("range") range: String? = null,
        @Query("lat") lat: String = "35.669220",
        @Query("lng") lng: String = "139.761457",
        @Query("genre") genre: String? = null,
        @Query("count") count: String? = null,
        @Query("wifi") wifi: String? = null,
        @Query("course") course: String? = null,
        @Query("free_drink") freeDrink: String? = null,
        @Query("free_food") freeFood: String? = null,
        @Query("private_room") privateRoom: String? = null,
        @Query("horigotatsu") horigotatsu: String? = null,
        @Query("tatami") tatami: String? = null,
        @Query("cocktail") cocktail: String? = null,
        @Query("shochu") shochu: String? = null,
        @Query("sake") sake: String? = null,
        @Query("wine") wine: String? = null,
        @Query("card") card: String? = null,
        @Query("non_smoking") nonSmoking: String? = null,
        @Query("charter") charter: String? = null,
        @Query("ktai") ktai: String? = null,
        @Query("parking") parking: String? = null,
        @Query("barrier_free") barrierFree: String? = null,
        @Query("sommelier") sommelier: String? = null,
        @Query("night_view") nightView: String? = null,
        @Query("open_air") openAir: String? = null,
        @Query("show") show: String? = null,
        @Query("equipment") equipment: String? = null,
        @Query("karaoke") karaoke: String? = null,
        @Query("band") band: String? = null,
        @Query("tv") tv: String? = null,
        @Query("lunch") lunch: String? = null,
        @Query("midnight") midnight: String? = null,
        @Query("midnight_meal") midnightMeal: String? = null,
        @Query("english") english: String? = null,
        @Query("pet") pet: String? = null,
        @Query("child") child: String? = null,
    ): Response<GourmetData>

    data class GenreData(
        val results: GenreResults?
    )
    data class GenreResults(
        val genre: List<GenreCode>
    )
    data class GenreCode(
        val code: String,
        val name: String
    )
    data class GourmetData(
        val results: GourmetResults?
    )
    data class GourmetResults(
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
        val photo: Photo,
        val open: String
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