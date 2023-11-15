package com.fortetigre.gamibrjo.data

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.fortetigre.gamibrjo.BALANCE
import com.fortetigre.gamibrjo.IS_SOUND
import com.fortetigre.gamibrjo.IS_TIMER

object CommonInfo {
    val balanceLD = MutableLiveData<Int>()

    var settings: Settings = Settings()

    var prefs: SharedPreferences? = null


    fun setupPrefs(myPrefs: SharedPreferences) {
        prefs = myPrefs
        getSettings()
        initBalance()
    }
    fun increaseBalance(value:Int) {
        val oldValue = prefs?.getInt(BALANCE, 0)?:0
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

    fun getSettings(){
        settings = Settings().apply {
            this.isSound = prefs?.getBoolean(IS_SOUND, false) ?: false
            this.isTimer = prefs?.getBoolean(IS_TIMER, false) ?: false
        }
    }
    private fun initBalance(){
        balanceLD.value = prefs?.getInt(BALANCE, 0)
    }
}