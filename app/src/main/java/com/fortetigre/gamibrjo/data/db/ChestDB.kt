package com.fortetigre.gamibrjo.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class ChestDB(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val isChosen:Boolean,
    val isBuying:Boolean,
    val chestImgBuying:Int,
    val chestImgNotBuying:Int,
    val price:Int,
    val crystalId:Int,
){
    fun getChestImgId()=if (isBuying) chestImgBuying else chestImgNotBuying

    fun getBtnImgId(): Int {
        return if (isBuying){
            if (isChosen) BtnTypes.CHOSEN.btnId
            else BtnTypes.CHOSE.btnId
        }else BtnTypes.BUY.btnId
    }

    fun btnClicked():ChestDB?{
        if (isBuying){
            if (isChosen) return null
            else return ChestDB(id,name,true,isBuying,chestImgBuying, chestImgNotBuying, price, crystalId)
        }else return ChestDB(id,name, false, true, chestImgBuying, chestImgNotBuying, price, crystalId)
    }

    fun cancelChosen():ChestDB{
        return ChestDB(id,name,false,isBuying,chestImgBuying, chestImgNotBuying,price, crystalId)
    }
}
