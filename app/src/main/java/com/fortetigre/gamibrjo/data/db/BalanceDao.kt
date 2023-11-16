package com.fortetigre.gamibrjo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BalanceDao {

    @Query("SELECT BalanceDB.balance FROM BalanceDB LIMIT 1")
    suspend fun getCurrentBalanceValue():Int

    @Query("SELECT BalanceDB.balance FROM BalanceDB LIMIT 1")
    fun getBalanceLD():LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun increaseBalance(balance:BalanceDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun decreaseBalance(balance:BalanceDB)


}