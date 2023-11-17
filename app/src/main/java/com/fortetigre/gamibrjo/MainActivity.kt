package com.fortetigre.gamibrjo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.fortetigre.gamibrjo.data.CommonInfo
import com.fortetigre.gamibrjo.data.Settings
import com.fortetigre.gamibrjo.data.db.AppDatabase
import com.fortetigre.gamibrjo.data.db.ChestDB
import com.fortetigre.gamibrjo.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    private val chestDao by lazy { AppDatabase.getInstance(application).ChestDao() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initCommonInfo()
        initChest()
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

    private fun initChest(){
        lifecycleScope.launch(Dispatchers.IO) {
            if (chestDao.getChestList().size<=1){
                val greyChest = ChestDB(0,"GREY",true,true,R.drawable.chest_grey,R.drawable.chest_grey,0, R.drawable.crystal_grey)
                chestDao.addChest(greyChest)
                val blueChest = ChestDB(0,"BLUE",false,false,R.drawable.chest_blue,R.drawable.chest_grey_buy, 500, R.drawable.crystal_blue)
                chestDao.addChest(blueChest)
                val redChest = ChestDB(0,"RED",false,false,R.drawable.chest_red,R.drawable.chest_red_buy,1000, R.drawable.crystal_red)
                chestDao.addChest(redChest)
                val greenChest = ChestDB(0,"GREEN",false,false,R.drawable.chest_green,R.drawable.chest_green_buy,1500, R.drawable.crystal_green)
                chestDao.addChest(greenChest)
            }
        }
    }

}