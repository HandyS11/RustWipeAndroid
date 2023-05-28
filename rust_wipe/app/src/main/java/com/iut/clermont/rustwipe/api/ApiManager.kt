package com.iut.clermont.rustwipe.api

import android.util.Log
import com.iut.clermont.rustwipe.api.data.Data
import com.iut.clermont.rustwipe.api.data.MapData
import com.iut.clermont.rustwipe.database.RustDatabase
import com.iut.clermont.rustwipe.database.data.toModel
import com.iut.clermont.rustwipe.database.repository.ServerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {

    private fun buildServer(): RustService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.battlemetrics.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(RustService::class.java)
    }

    private fun buildMap(): MapService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://rustmaps.com/api/v3/maps/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(MapService::class.java)
    }

    fun getServers(number: Int) {
        val serverRepo = ServerRepository(RustDatabase.getInstance().serverDao())
        if (serverRepo.getCount() < 5) {
            buildServer().getSome(number).enqueue(object : Callback<Data> {
                override fun onResponse(call: Call<Data>, response: Response<Data>) {
                    val servers = response.body()?.data
                    if (servers != null) {
                        for (server in servers) {
                            CoroutineScope(Dispatchers.IO).launch {
                                serverRepo.insert(server.toModel())
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<Data>, t: Throwable) {
                    Log.e("RustWipeServer: GET", "Error while getting data.\n"
                            + call.request().url().toString() + "\n"
                            + t.stackTraceToString())
                }
            })
        }
        else {
            Log.e("RustWipeAPI: ", "Enough servers no need to fill the database!")
        }
    }

    fun getServersWithRegion(number: Int, country: String) {
        buildServer().getWithCountry(number, country).enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                val serverRepo = ServerRepository(RustDatabase.getInstance().serverDao())
                val servers = response.body()?.data
                if (servers != null) {
                    for (server in servers) {
                        CoroutineScope(Dispatchers.IO).launch {
                            serverRepo.insert(server.toModel())
                        }
                    }
                }
            }
            override fun onFailure(call: Call<Data>, t: Throwable) {
                Log.e("RustWipeServer: GET", "Error while getting data.\n"
                        + call.request().url().toString() + "\n"
                        + t.stackTraceToString())
            }
        })
    }

    fun getServersWithName(number: Int, name: String) {
        buildServer().getWithName(number, name).enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                val serverRepo = ServerRepository(RustDatabase.getInstance().serverDao())
                val servers = response.body()?.data
                if (servers != null) {
                    for (server in servers) {
                        CoroutineScope(Dispatchers.IO).launch {
                            serverRepo.insert(server.toModel())
                        }
                    }
                }
            }
            override fun onFailure(call: Call<Data>, t: Throwable) {
                Log.e("RustWipeServer: GET", "Error while getting data.\n"
                        + call.request().url().toString() + "\n"
                        + t.stackTraceToString())
            }
        })
    }

    fun getMap(seed: Long, size: Int, isBarren: Boolean) {
        buildMap().getMap(seed, size, isBarren).enqueue(object : Callback<MapData> {
            override fun onResponse(call: Call<MapData>, response: Response<MapData>) {
                Log.e("RustWipeMap: GET", "MAP: " + println(response.body()))
            }
            override fun onFailure(call: Call<MapData>, t: Throwable) {
                Log.e("RustWipeMap: GET", "Error while getting map.\n"
                + call.request().url().toString() + "\n"
                + t.stackTraceToString())
            }
        })
    }
}