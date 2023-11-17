package com.fortetigre.gamibrjo

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.fortetigre.gamibrjo.data.CommonInfo
import com.fortetigre.gamibrjo.data.db.AppDatabase
import com.fortetigre.gamibrjo.data.db.BalanceDB
import com.fortetigre.gamibrjo.databinding.FragmentGameBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates
import kotlin.random.Random

class GameFragment : Fragment() {

    private val binding by lazy { FragmentGameBinding.inflate(layoutInflater) }
    private val xStart by lazy { binding.topLine.x }
    private val xEnd by lazy { xStart + (binding.mainBackground.width).toFloat() }
    private val yStart by lazy { binding.topLine.y }
    private val yEnd by lazy { binding.bottomLine.y }
    private val xFailure by lazy { ((binding.bottomChest).width / 2).toFloat() }
    private val yFailure by lazy { binding.bottomChest.y + binding.bottomChest.height }
    private val ivCrystal by lazy { binding.ivCrystal }
    private var isAnimationActive = false
    private val chestDao by lazy {
        AppDatabase.getInstance(requireActivity().application).ChestDao()
    }
    private val balanceDao by lazy {
        AppDatabase.getInstance(requireActivity().application).BalanceDao()
    }
    private val tvBalance by lazy { binding.balance.tvBalance }
    private var isCrystalAttached = false
    private val progress by lazy { binding.progressBar }
    private var crystalId =0
    private val startTimeInMills by lazy { Calendar.getInstance().timeInMillis }
    private var startBalance = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            crystalId = chestDao.getChosenChestCrystal()
            startBalance = balanceDao.getCurrentBalanceValue()
            delay(1000)
            createCrystalView()
            launchTimer()
        }
        observeBalance()
        launchTimer()
        setupBtnBackClickListener()
    }

    private fun launchTimer() {
        if (CommonInfo.settings.isTimer){
            progress.max = 30
            var counter = 0
            lifecycleScope.launch {
                while (counter <= 30) {
                    delay(1000)
                    counter++
                    progress.setProgress(counter, true)
                }
                launchResult()
            }
        }else{
            progress.visibility = View.GONE
            binding.sandWatches.visibility = View.GONE
        }

    }

    private fun launchResult(){
        val endTimeInMils = Calendar.getInstance().timeInMillis
        val gameTimeInSeconds = ((endTimeInMils +1000 - startTimeInMills)/1000).toInt()
        var endBalance = 0
        lifecycleScope.launch {
            endBalance = balanceDao.getCurrentBalanceValue()
        }
        val gameResult = endBalance - startBalance
    }

    private fun setupBtnBackClickListener(){
        launchResult()
    }

    private fun observeBalance() {
        makeGradientText()
        balanceDao.getBalanceLD().observe(viewLifecycleOwner) {
            tvBalance.text = it.toString()
        }
    }

    private fun makeGradientText() {
        val paint = tvBalance.paint
        val height = paint.measureText(tvBalance.text.toString())
        val textShader: Shader = LinearGradient(
            50f, 0f, height, tvBalance.textSize, intArrayOf(
                Color.parseColor("#F9AA2C"),
                Color.parseColor("#BC1F1C"),
            ), null, Shader.TileMode.REPEAT
        )
        tvBalance.paint.shader = textShader
    }

    private fun createCrystalView() {
        val crystal = ImageView(requireContext())
        crystal.setImageDrawable(requireContext().getDrawable(crystalId))
        crystal.rotation = (Random.nextFloat()) * 100
        var randomScale = (Random.nextDouble(2.0)).toFloat()
        if (randomScale <= 0.7) randomScale += 1
        crystal.scaleX = randomScale
        crystal.scaleY = randomScale
        val limitY = binding.balance.root.height + crystal.height
        val randomX = (Random.nextInt(xEnd.toInt() - ivCrystal.width)).toFloat()
        val randomY = (Random.nextInt(yEnd.toInt() - limitY)).toFloat()
        crystal.x = randomX
        crystal.y = randomY
        crystal.visibility = View.VISIBLE
        if (!isCrystalAttached) {
            binding.root.addView(crystal)
            isCrystalAttached = true
        } else {
            lifecycleScope.launch {
                delay(100)
                binding.root.addView(crystal)
                isCrystalAttached = true
            }
        }
        crystal.setOnClickListener {
            if (crystal.x == randomX) launchSuccessAnim(crystal)
        }
        lifecycleScope.launch {
            delay(1000)
            if (crystal.x == randomX) {
                launchFailureAnim(crystal)
            }
        }
    }

    private fun launchSuccessAnim(crystalView: ImageView) {
        crystalView.animate().apply {
            duration = 500
            translationX(binding.balance.root.x)
            translationY(binding.btnBack.y)
            scaleX(0.1F)
            scaleY(0.1F)
            withEndAction {
                binding.root.removeView(crystalView)
                isCrystalAttached = false
                createCrystalView()
                lifecycleScope.launch {
                    val currentBalance = balanceDao.getCurrentBalanceValue()
                    balanceDao.increaseBalance(BalanceDB(1, (currentBalance + 20)))
                }
            }
        }
    }

    private fun launchFailureAnim(crystalView: ImageView) {
        isAnimationActive = true
        crystalView.animate().apply {
            duration = 500
            translationX(xFailure)
            translationY(yFailure)
            scaleX(0.1F)
            scaleY(0.1F)
            withEndAction {
                binding.root.removeView(crystalView)
                isCrystalAttached = false
                createCrystalView()
                lifecycleScope.launch {
                    val currentBalance = balanceDao.getCurrentBalanceValue()
                    balanceDao.decreaseBalance(BalanceDB(1, currentBalance - 20))
                }
            }
        }
    }

}