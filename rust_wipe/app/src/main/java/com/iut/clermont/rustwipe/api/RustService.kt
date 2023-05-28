package com.iut.clermont.rustwipe.api

import com.iut.clermont.rustwipe.api.data.Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RustService {

    @GET("servers?filter[game]=rust&fields[server]=name%2Cip%2Cport%2Ccountry%2Cplayers%2CmaxPlayers%2Crank%2Cdetails")
    fun getSome(@Query("page[size]") size: Int): Call<Data>

    @GET("servers?filter[game]=rust&fields[server]=name%2Cip%2Cport%2Ccountry%2Cplayers%2CmaxPlayers%2Crank%2Cdetails")
    fun getWithCountry(@Query("page[size]") size: Int, @Query("filter[countries][]") country: String): Call<Data>

    @GET("servers?filter[game]=rust&fields[server]=name%2Cip%2Cport%2Ccountry%2Cplayers%2CmaxPlayers%2Crank%2Cdetails")
    fun getWithName(@Query("page[size]") size: Int, @Query("filter[search]") name: String): Call<Data>
}