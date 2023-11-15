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
import com.fortetigre.gamibrjo.databinding.FragmentWelcomeBonusBinding

class WelcomeBonusFragment : Fragment() {

    private val binding by lazy { FragmentWelcomeBonusBinding.inflate(layoutInflater) }
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
        observeBalance()
        setupBonusClickListener()
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

    private fun setupBonusClickListener(){
        binding.bonus.setOnClickListener {
            CommonInfo.increaseBalance(1000)
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainContainer, StartGameFragment())
                commit()
            }
        }
    }

}