package com.fortetigre.gamibrjo.data

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.fortetigre.gamibrjo.BALANCE
import com.fortetigre.gamibrjo.BLUE_IS_BUY
import com.fortetigre.gamibrjo.CHOSEN_CHEST
import com.fortetigre.gamibrjo.GREEN_IS_BUY
import com.fortetigre.gamibrjo.IS_SOUND
import com.fortetigre.gamibrjo.IS_TIMER
import com.fortetigre.gamibrjo.R
import com.fortetigre.gamibrjo.RED_IS_BUY

object CommonInfo {
    val balanceLD = MutableLiveData<Int>()

    var settings: Settings = Settings()

    var prefs: SharedPreferences? = null

    val listOfChest = MutableLiveData<List<Chest>>()


    fun setupPrefs(myPrefs: SharedPreferences) {
        prefs = myPrefs
        getSettings()
        initBalance()
    }

    fun increaseBalance(value: Int) {
        val oldValue = prefs?.getInt(BALANCE, 0) ?: 0
        val newValue = oldValue + value
        prefs?.edit()?.putInt(BALANCE, newValue)
        balanceLD.value = newValue
    }

    fun changeSettings(mySettings: Settings) {
        settings = mySettings
        prefs?.edit()?.apply {
            putBoolean(IS_TIMER, mySettings.isTimer)?.apply()
            putBoolean(IS_SOUND, mySettings.isSound)?.apply()
        }
    }

    fun getSettings() {
        settings = Settings().apply {
            this.isSound = prefs?.getBoolean(IS_SOUND, false) ?: false
            this.isTimer = prefs?.getBoolean(IS_TIMER, false) ?: false
            this.chosenChest = prefs?.getInt(CHOSEN_CHEST, 1) ?: 1
        }
    }

    private fun initBalance() {
        balanceLD.value = prefs?.getInt(BALANCE, 0)
    }

    fun choseChest(chestNum:Int){
        prefs?.edit()?.putInt(CHOSEN_CHEST, chestNum)?.apply()
        settings.chosenChest = chestNum
    }

    fun buyChest(chestNum:Int){
        when(chestNum){
            2 -> prefs?.edit()?.putBoolean(BLUE_IS_BUY, true)?.apply()
            3 -> prefs?.edit()?.putBoolean(RED_IS_BUY, true)?.apply()
            else -> prefs?.edit()?.putBoolean(GREEN_IS_BUY, true)?.apply()
        }
    }

    fun getChestStatus(chestNum:Int):Boolean{
        return when(chestNum){
            1 -> true
            2 -> prefs?.getBoolean(BLUE_IS_BUY, false)?:false
            3 -> prefs?.getBoolean(RED_IS_BUY, false)?:false
            else -> prefs?.getBoolean(GREEN_IS_BUY, false)?:false

        }
    }

    fun fetchListChest(){
        val listChest = mutableListOf<Chest>()
        val chosenChest = getChosenChest()
        val btnChosen = R.drawable.choosen_btn
        val btnChose = R.drawable.choose_btn
        val btnBuy = R.drawable.buy_btn
        var btnImg = 0
        var chestImg =0
        var isChestChosen = false
        //Grey
        isChestChosen = chosenChest==1
        btnImg = if (isChestChosen) btnChosen else btnChose
        chestImg = R.drawable.chest_grey
        listChest.add(Chest(1,isChestChosen,true, chestImg, btnImg))
        //Blue
        var btnImg2=0
        var chestImg2=0
        val isChestChosen2 = chosenChest==2
        val isBuying2 = getChestStatus(2)
        if (isBuying2){
            btnImg2 = if (isChestChosen2) btnChosen else btnChose
            chestImg2 = R.drawable.chest_blue
        }else{
            btnImg2 = btnBuy
            chestImg2 = R.drawable.chest_grey_buy
        }
        listChest.add(Chest(2,isChestChosen2,isBuying2, chestImg2, btnImg2))
        //Red
        var btnImg3=0
        var chestImg3=0
        val isChestChosen3 = chosenChest==2
        val isBuying3 = getChestStatus(3)
        if (isBuying3){
            btnImg3 = if (isChestChosen3) btnChosen else btnChose
            chestImg3 = R.drawable.chest_red
        }else{
            btnImg3 = btnBuy
            chestImg3 = R.drawable.chest_red_buy
        }
        listChest.add(Chest(3,isChestChosen3,isBuying3, chestImg3, btnImg3))
        //Green
        var btnImg4=0
        var chestImg4=0
        val isChestChosen4 = chosenChest==2
        val isBuying4 = getChestStatus(4)
        if (isBuying4){
            btnImg4 = if (isChestChosen4) btnChosen else btnChose
            chestImg4 = R.drawable.chest_green
        }else{
            btnImg4 = btnBuy
            chestImg4 = R.drawable.chest_green_buy
        }
        listChest.add(Chest(4,isChestChosen4,isBuying4, chestImg4, btnImg4))
        listOfChest.value = listChest
    }

    fun changeBtn(btnNumber: Int) {
        if (getChestStatus(btnNumber)) {
            choseChest(btnNumber)
        } else {
            buyChest(btnNumber)
        }
        fetchListChest()
    }

    fun getChosenChest():Int{
        return prefs?.getInt(CHOSEN_CHEST, 1)?:1
    }
}