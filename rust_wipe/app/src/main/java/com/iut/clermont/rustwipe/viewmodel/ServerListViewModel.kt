package com.iut.clermont.rustwipe.viewmodel

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iut.clermont.rustwipe.database.RustDatabase
import com.iut.clermont.rustwipe.database.data.Server
import com.iut.clermont.rustwipe.database.repository.ServerRepository
import kotlinx.coroutines.launch

class ServerListViewModel : ViewModel() {
    private val serverRepo = ServerRepository(RustDatabase.getInstance().serverDao())

    val serverList = serverRepo.getAll()
    val showEmptyView = Transformations.map(serverList, List<Server>::isEmpty)

    fun delete(server: Server) = viewModelScope.launch {
        serverRepo.delete(server)
    }
}