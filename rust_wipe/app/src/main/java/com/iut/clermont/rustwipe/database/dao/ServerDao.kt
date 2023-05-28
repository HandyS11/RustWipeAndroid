package com.iut.clermont.rustwipe.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.iut.clermont.rustwipe.database.data.Server

@Dao
interface ServerDao {

    @Query("SELECT * FROM rust_server")
    fun getAllServers(): LiveData<List<Server>>

    @Query("SELECT * FROM rust_server WHERE id = :id")
    fun getServerWithId(id: Long): LiveData<Server>

    @Query("SELECT * FROM rust_server ORDER BY rank")
    fun getServersOrderByRank(): LiveData<List<Server>>

    //@Query("SELECT * FROM rust_server WHERE name LIKE %:name%")
    //fun getServersWithName(name: String): LiveData<List<Server>>

    @Query("SELECT COUNT(*) FROM rust_server")
    fun getServerCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(server: Server)

    @Update(onConflict = REPLACE)
    suspend fun updateServer(server: Server)

    @Delete
    suspend fun delete(server: Server)

    @Query("DELETE FROM rust_server")
    suspend fun deleteAll()
}