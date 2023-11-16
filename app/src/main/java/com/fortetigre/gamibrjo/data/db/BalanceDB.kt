package com.fortetigre.gamibrjo.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BalanceDB(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val balance:Int
)
