package com.fortetigre.gamibrjo

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fortetigre.gamibrjo.data.CommonInfo
import com.fortetigre.gamibrjo.data.Settings
import com.fortetigre.gamibrjo.databinding.FragmentSettingsGameBinding

class SettingsGameFragment : Fragment() {

    private val binding by lazy { FragmentSettingsGameBinding.inflate(layoutInflater) }
    private val commonInfo by lazy { CommonInfo }
    private val tvBalance by lazy { binding.balance.tvBalance }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSettings()
        setupBtnYesClickListener()
        observeBalance()
    }

    private fun initSettings(){
        if (commonInfo.settings.isSound) binding.swSound.isChecked = true
        if (commonInfo.settings.isTimer) binding.swTimer.isChecked = true
    }

    private fun setupBtnYesClickListener(){
        binding.btnYes.setOnClickListener {
            val settings = Settings()
            settings.isSound = binding.swSound.isChecked
            settings.isTimer = binding.swTimer.isChecked
            commonInfo.changeSettings(settings)
            parentFragmentManager.popBackStack()
        }
    }

    private fun observeBalance(){
        makeGradientText()
        commonInfo.balanceLD.observe(viewLifecycleOwner){
            tvBalance.text = it.toString()
        }
    }

    private fun makeGradientText(){
        val paint = tvBalance.paint
        val height = paint.measureText(tvBalance.text.toString())
        val textShader: Shader = LinearGradient(50f, 0f, height, tvBalance.textSize, intArrayOf(
            Color.parseColor("#F9AA2C"),
            Color.parseColor("#BC1F1C"),
        ), null, Shader.TileMode.REPEAT)

        tvBalance.paint.setShader(textShader)
    }

}