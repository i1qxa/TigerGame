package com.fortetigre.gamibrjo.data

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.fortetigre.gamibrjo.BALANCE
import com.fortetigre.gamibrjo.CHOSEN_CHEST
import com.fortetigre.gamibrjo.IS_SOUND
import com.fortetigre.gamibrjo.IS_TIMER

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


}