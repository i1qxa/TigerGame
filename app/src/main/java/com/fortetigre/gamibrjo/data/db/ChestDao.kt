package com.fortetigre.gamibrjo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChestDao {

    @Query("SELECT * FROM ChestDB")
    suspend fun getChestList():List<ChestDB>

    @Query("SELECT * FROM ChestDB")
    fun getLDChestItems():LiveData<List<ChestDB>>

    @Query("SELECT * FROM ChestDB WHERE name !=:chosen")
    suspend fun getChestListToCancelChosen(chosen:String):List<ChestDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addChest(chest:ChestDB)

}