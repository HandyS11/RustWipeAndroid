package com.iut.clermont.rustwipe.viewmodel;

import androidx.lifecycle.ViewModel
import com.iut.clermont.rustwipe.database.RustDatabase
import com.iut.clermont.rustwipe.database.data.NEW_SERVER_ID
import com.iut.clermont.rustwipe.database.repository.ServerRepository

class ServerPagerViewModel : ViewModel() {
    private val serverRepo = ServerRepository(RustDatabase.getInstance().serverDao())

    val serverList = serverRepo.getAll()
    var currentServerId = NEW_SERVER_ID
}
