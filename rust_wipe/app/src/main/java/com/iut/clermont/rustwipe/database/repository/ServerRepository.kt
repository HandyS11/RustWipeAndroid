package com.iut.clermont.rustwipe.database.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.iut.clermont.rustwipe.database.dao.ServerDao
import com.iut.clermont.rustwipe.database.data.Server

class ServerRepository(private val serverDao: ServerDao) {

    @WorkerThread
    fun getAll(): LiveData<List<Server>> {
        return serverDao.getAllServers();
    }

    @WorkerThread
    fun getAllByRank(): LiveData<List<Server>> {
        return serverDao.getServersOrderByRank();
    }

    /*@WorkerThread
    fun getAllWithName(name: String): LiveData<List<Server>> {
        return serverDao.getServersWithName(name)
    }*/

    @WorkerThread
    fun getWithId(id: Long): LiveData<Server> {
        return serverDao.getServerWithId(id)
    }

    @WorkerThread
    fun getCount(): Int {
        return serverDao.getServerCount()
    }

    @WorkerThread
    suspend fun insert(server: Server) {
        serverDao.insert(server)
    }

    @WorkerThread
    suspend fun update(server: Server) {
        serverDao.updateServer(server)
    }

    @WorkerThread
    suspend fun delete(server: Server) {
        serverDao.delete(server)
    }

    @WorkerThread
    suspend fun deleteAll() {
        serverDao.deleteAll()
    }
}