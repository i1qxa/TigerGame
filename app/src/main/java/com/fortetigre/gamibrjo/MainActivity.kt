package com.fortetigre.gamibrjo

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.Animation
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private val xStart by lazy { findViewById<View>(R.id.topLine).x }
    private val xEnd by lazy { xStart + (findViewById<ImageView>(R.id.mainBackground).width).toFloat() }
    private val yStart by lazy { findViewById<View>(R.id.topLine).y }
    private val yEnd by lazy { findViewById<View>(R.id.bottomLine).y }
    private val xFailure by lazy { ((findViewById<ImageView>(R.id.bottomChest).width) / 2).toFloat() }
    private val yFailure by lazy {
        (findViewById<ImageView>(R.id.bottomChest).y) + (findViewById<ImageView>(
            R.id.bottomChest
        ).height)
    }
    private val ivCrystal by lazy { findViewById<ImageView>(R.id.ivCrystal) }
    private var isAnimationActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            delay(1000)
            setupRandomCrystal()
        }
        setupCrystalClickListener()
    }


    private fun setupCrystalClickListener() {
        ivCrystal.setOnClickListener {
            if (!isAnimationActive) {
                isAnimationActive = true
                val startX = ivCrystal.x
                val startY = ivCrystal.y
                launchSuccessAnim()
                lifecycleScope.launch {
                    delay(510)
                    launchBackAnimation(startX, startY)
                    setupRandomCrystal()
                }
            }
        }
    }


    private fun setupRandomCrystal() {
        isAnimationActive = false
        val img = R.drawable.crystal_blue
        ivCrystal.setImageDrawable(getDrawable(img))
        ivCrystal.rotation = (Random.nextFloat()) * 100
        var randomScale = (Random.nextDouble(2.0)).toFloat()
        if (randomScale <= 0.7) randomScale += 1
        ivCrystal.scaleX = randomScale
        ivCrystal.scaleY = randomScale
        val randomX = (Random.nextInt(xEnd.toInt() - ivCrystal.width)).toFloat()
        val randomY = (Random.nextInt(yEnd.toInt() - ivCrystal.height)).toFloat()
        ivCrystal.x = randomX
        ivCrystal.y = randomY
        Log.d("COORDINATE", "x:$randomX / y:$randomY")
        lifecycleScope.launch {
            delay(1000)
            if (!isAnimationActive) {
                isAnimationActive = true
                launchFailureAnim()
//                delay(510)
//                launchBackAnimation(randomX, randomY)
//                setupRandomCrystal()
            }
        }
    }

    private fun launchSuccessAnim() {
        isAnimationActive=true
        val startXCoordinate = ivCrystal.x
        val startYCoordinate = ivCrystal.y
        ivCrystal.animate().apply {
            duration = 500
            translationX(xEnd)
            translationY(yStart)
            scaleX(0.1F)
            scaleY(0.1F)
            withEndAction {
                launchBackAnimation(startXCoordinate, startYCoordinate)
            }
        }
    }

    private fun launchFailureAnim() {
        val startXCoordinate = ivCrystal.x
        val startYCoordinate = ivCrystal.y
        isAnimationActive=true
        ivCrystal.animate().apply {
            duration = 500
            translationX(xFailure)
            translationY(yFailure)
            scaleX(0.1F)
            scaleY(0.1F)
            withEndAction {
                launchBackAnimation(startXCoordinate, startYCoordinate)
            }
        }
    }

    private fun launchBackAnimation(startX:Float, startY:Float){
        isAnimationActive=true
        ivCrystal.animate().apply {
            duration = 1
            translationX(startX)
            translationY(startY)
            scaleX(1F)
            scaleY(1F)
            withEndAction {
                setupRandomCrystal()
            }
        }
    }
}