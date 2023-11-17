package com.fortetigre.gamibrjo.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi

@Database(
    entities = [
        BalanceDB::class,
        ChestDB::class
    ], version = 1,
    exportSchema = false
)
abstract class AppDatabase:RoomDatabase() {

    abstract fun BalanceDao():BalanceDao

    abstract fun ChestDao():ChestDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "app_db"

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let {
                return it
            }
            kotlinx.coroutines.internal.synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }
    }
}