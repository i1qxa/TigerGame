package com.fortetigre.gamibrjo

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fortetigre.gamibrjo.data.Chest
import com.fortetigre.gamibrjo.data.CommonInfo
import com.fortetigre.gamibrjo.databinding.FragmentShopGameBinding

class ShopGameFragment : Fragment() {

    private val binding by lazy { FragmentShopGameBinding.inflate(layoutInflater) }
    private val tvBalance by lazy { binding.balance.tvBalance }
    private val commonInfo by lazy { CommonInfo }
    private val listOfChoseBtn by lazy {
        listOf(
            binding.chestGrey.chestStatus,
            binding.chestBlue.chestStatus,
            binding.chestRed.chestStatus,
            binding.chestGreen.chestStatus,
        )
    }
    private val listOfChest by lazy {
        listOf(
            binding.chestGrey.ivChest,
            binding.chestBlue.ivChest,
            binding.chestRed.ivChest,
            binding.chestGreen.ivChest,
        )
    }

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
        initChest()
        observeChestList()
    }

    private fun observeBalance() {
        CommonInfo.balanceLD.observe(viewLifecycleOwner) {
            tvBalance.text = it.toString()
        }
        makeGradientText()
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

        tvBalance.paint.setShader(textShader)
    }

    private fun setupBtnClickListeners() {
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.chestGrey.chestStatus.setOnClickListener {
            commonInfo.changeBtn(1)
        }
        binding.chestBlue.chestStatus.setOnClickListener {
            commonInfo.changeBtn(2)
        }
        binding.chestRed.chestStatus.setOnClickListener {
            commonInfo.changeBtn(3)
        }
        binding.chestGreen.chestStatus.setOnClickListener {
            commonInfo.changeBtn(4)
        }
    }

    private fun observeChestList() {
        commonInfo.listOfChest.observe(viewLifecycleOwner) {
            with(binding.chestGrey) {
                ivChest.setImageDrawable(requireContext().getDrawable(it[0].chestImg))
                chestStatus.setImageDrawable(requireContext().getDrawable(it[0].btnImg))
            }
            with(binding.chestBlue) {
                ivChest.setImageDrawable(requireContext().getDrawable(it[1].chestImg))
                chestStatus.setImageDrawable(requireContext().getDrawable(it[1].btnImg))
            }
            with(binding.chestRed) {
                ivChest.setImageDrawable(requireContext().getDrawable(it[2].chestImg))
                chestStatus.setImageDrawable(requireContext().getDrawable(it[2].btnImg))
            }
            with(binding.chestGreen) {
                ivChest.setImageDrawable(requireContext().getDrawable(it[3].chestImg))
                chestStatus.setImageDrawable(requireContext().getDrawable(it[3].btnImg))
            }
        }
    }

    private fun initChest() {
        commonInfo.fetchListChest()
    }

//    private fun changeBtn(btnNumber: Int) {
//        if (commonInfo.getChestStatus(btnNumber)) {
//            commonInfo.choseChest(btnNumber)
//        } else {
//            commonInfo.buyChest(btnNumber)
//        }
//        initChest()
//    }

}