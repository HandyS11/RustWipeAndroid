package com.iut.clermont.rustwipe

import android.app.Application
import com.iut.clermont.rustwipe.api.ApiManager
import com.iut.clermont.rustwipe.database.RustDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RustWipeApplication : Application() {
    private val apiManager by lazy { ApiManager() }

    override fun onCreate() {
        super.onCreate()
        RustDatabase.initialize(this)
        CoroutineScope(Dispatchers.IO).launch {
            apiManager.getServers(10)
        }
    }
}