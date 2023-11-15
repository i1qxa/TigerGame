package com.fortetigre.gamibrjo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.fortetigre.gamibrjo.data.CommonInfo
import com.fortetigre.gamibrjo.data.Settings
import com.fortetigre.gamibrjo.databinding.ActivityMainBinding

const val TIGER_PREFS = "tiger_prefs"
const val IS_SOUND = "is_sound"
const val IS_TIMER = "is_timer"
const val IS_FIRST_LAUNCH = "is_first_launch"
const val BALANCE = "balance"
const val CHOSEN_CHEST = "chosen_chest"
const val BLUE_IS_BUY = "blue_is_buy"
const val RED_IS_BUY = "red_is_buy"
const val GREEN_IS_BUY = "green_is_buy"
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val prefs by lazy { this.getSharedPreferences(TIGER_PREFS, Context.MODE_PRIVATE) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initCommonInfo()
    }

    private fun initCommonInfo(){
        val commonInfo = CommonInfo
        commonInfo.setupPrefs(prefs)
        val isFirstLaunch = prefs.getBoolean(IS_FIRST_LAUNCH, true)
        if (isFirstLaunch){
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.mainContainer, WelcomeBonusFragment())
                commit()
            }
            prefs.edit().putBoolean(IS_FIRST_LAUNCH, false).apply()
            prefs.edit().putInt(CHOSEN_CHEST, 1).apply {  }
        }
    }

}