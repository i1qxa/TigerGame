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
import com.fortetigre.gamibrjo.databinding.FragmentStartGameBinding

class StartGameFragment : Fragment() {

    private val binding by lazy { FragmentStartGameBinding.inflate(layoutInflater) }
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
        setupBtnClickListeners()
    }

    private fun setupBtnClickListeners(){
        binding.btnSettings.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, SettingsGameFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.btnShop.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainContainer, ShopGameFragment())
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun observeBalance(){
        CommonInfo.balanceLD.observe(viewLifecycleOwner){
            tvBalance.text = it.toString()
        }
        makeGradientText()
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