package com.iut.clermont.rustwipe.api

import com.iut.clermont.rustwipe.api.data.MapData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MapService {

    @Headers("X-API-Key: enterYourKeyThere")
    @GET("{seed}/{size}")
    fun getMap(@Path("seed") seed: Long, @Path("size") size: Int, @Query("barren[isBarren]") isBarren: Boolean): Call<MapData>
}