package com.fortetigre.gamibrjo.data

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.fortetigre.gamibrjo.BALANCE
import com.fortetigre.gamibrjo.BLUE_IS_BUY
import com.fortetigre.gamibrjo.CHOSEN_CHEST
import com.fortetigre.gamibrjo.GREEN_IS_BUY
import com.fortetigre.gamibrjo.IS_SOUND
import com.fortetigre.gamibrjo.IS_TIMER
import com.fortetigre.gamibrjo.RED_IS_BUY

object CommonInfo {
    val balanceLD = MutableLiveData<Int>()

    var settings: Settings = Settings()

    var prefs: SharedPreferences? = null


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
            2 -> prefs?.edit()?.putBoolean(BLUE_IS_BUY, true)
            3 -> prefs?.edit()?.putBoolean(RED_IS_BUY, true)
            else -> prefs?.edit()?.putBoolean(GREEN_IS_BUY, true)
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

    fun getChosenChest():Int{
        return prefs?.getInt(CHOSEN_CHEST, 1)?:1
    }
}