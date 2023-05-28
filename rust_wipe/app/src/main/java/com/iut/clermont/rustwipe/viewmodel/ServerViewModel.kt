package com.iut.clermont.rustwipe.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iut.clermont.rustwipe.api.ApiManager
import com.iut.clermont.rustwipe.database.RustDatabase
import com.iut.clermont.rustwipe.database.data.NEW_SERVER_ID
import com.iut.clermont.rustwipe.database.repository.ServerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class ServerViewModel(serverId: Long) : ViewModel() {
    private val serverRepo = ServerRepository(RustDatabase.getInstance().serverDao())
    private val apiManager = ApiManager()

    val server = serverRepo.getWithId(serverId)

    fun saveServer() = server.value?.let {
        viewModelScope.launch {
            serverRepo.update(it)
        }
    }

    fun deleteServer() = viewModelScope.launch {
        server.value?.let {
            if (it.serverId != NEW_SERVER_ID) {
                serverRepo.delete(it)
            }
        }
    }

    fun getImage(): Bitmap? {
        var bitmap: Bitmap? = null
        server.value?.let {
            viewModelScope.launch {
                val map = apiManager.getMap(it.seed!!, it.size!!, false)
                try {
                    val img = URL("https://files.rustmaps.com/img/231/1403a42a-cecc-4cc9-9ca7-6e939a38d801/FullLabeledMap.png").openStream()
                    bitmap = BitmapFactory.decodeStream(img)
                }
                catch (e: Exception) {
                    Log.e("BITMAP", "Error while downloading bitmap! " + e.stackTraceToString())
                }
            }
        }
        return bitmap
    }
}