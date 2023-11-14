package com.fortetigre.gamibrjo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.fortetigre.gamibrjo.databinding.FragmentGameBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameFragment : Fragment() {

    private val binding by lazy { FragmentGameBinding.inflate(layoutInflater) }
    private val xStart by lazy { binding.topLine.x }
    private val xEnd by lazy { xStart + (binding.mainBackground.width).toFloat() }
    private val yStart by lazy { binding.topLine.y }
    private val yEnd by lazy { binding.bottomLine.y }
    private val xFailure by lazy { ((binding.bottomChest).width / 2).toFloat() }
    private val yFailure by lazy {binding.bottomChest.y + binding.bottomChest.height}
    private val ivCrystal by lazy { binding.ivCrystal }
    private var isAnimationActive = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        ivCrystal.setImageDrawable(requireContext().getDrawable(img))
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