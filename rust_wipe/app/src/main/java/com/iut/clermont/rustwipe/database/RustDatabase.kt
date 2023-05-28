package com.iut.clermont.rustwipe.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.iut.clermont.rustwipe.RustWipeApplication
import com.iut.clermont.rustwipe.database.dao.ServerDao
import com.iut.clermont.rustwipe.database.data.Server

private const val RUST_DB_FILENAME = "rust_database"

@Database(entities = [Server::class], version = 1)
abstract class RustDatabase : RoomDatabase() {

    abstract fun serverDao(): ServerDao

    companion object {
        private lateinit var application: Application

        @Volatile
        private var instance: RustDatabase? = null

        fun getInstance(): RustDatabase {
            if (::application.isInitialized) {
                if (instance == null)
                    synchronized(this) {
                        if (instance == null)
                            instance = Room.databaseBuilder(
                                application.applicationContext,
                                RustDatabase::class.java,
                                RUST_DB_FILENAME
                            )
                                .build()
                    }
                return instance!!
            } else {
                throw RuntimeException("The database must be initialized first!")
            }
        }

        @Synchronized
        fun initialize(app: RustWipeApplication) {
            if (::application.isInitialized) {
                throw RuntimeException("The database cannot be initialized twice!")
            }
            application = app
        }
    }
}