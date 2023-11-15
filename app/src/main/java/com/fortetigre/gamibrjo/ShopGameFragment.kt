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
import com.fortetigre.gamibrjo.databinding.FragmentShopGameBinding

class ShopGameFragment : Fragment() {

    private val binding by lazy { FragmentShopGameBinding.inflate(layoutInflater) }
    private val tvBalance by lazy { binding.balance.tvBalance }
    private val commonInfo by lazy { CommonInfo }
    private val listOfChoseBtn by lazy { listOf(
        binding.chestGrey.chestStatus,
        binding.chestBlue.chestStatus,
        binding.chestRed.chestStatus,
        binding.chestGreen.chestStatus,) }
    private val listOfChest by lazy { listOf(
        binding.chestGrey.ivChest,
        binding.chestBlue.ivChest,
        binding.chestRed.ivChest,
        binding.chestGreen.ivChest,) }
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

    private fun setupBtnClickListeners(){
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.chestGrey.chestStatus.setOnClickListener {
            changeBtn(1)
        }
        binding.chestBlue.chestStatus.setOnClickListener {
            changeBtn(2)
        }
        binding.chestRed.chestStatus.setOnClickListener {
            changeBtn(3)
        }
        binding.chestGreen.chestStatus.setOnClickListener {
            changeBtn(4)
        }
    }

    private fun initChest(){
        val chosenChest = commonInfo.getChosenChest()
        //Grey chest
        if (chosenChest == 1) binding.chestGrey.chestStatus.setImageDrawable(requireContext().getDrawable(R.drawable.choosen_btn))
        else binding.chestGrey.chestStatus.setImageDrawable(requireContext().getDrawable(R.drawable.choose_btn))
        //Blue chest
        if (commonInfo.getChestStatus(2)) {
            binding.chestBlue.ivChest.setImageDrawable(requireContext().getDrawable(R.drawable.chest_blue))
            if (chosenChest==2)binding.chestBlue.chestStatus.setImageDrawable(requireContext().getDrawable(R.drawable.choosen_btn))
            else binding.chestBlue.chestStatus.setImageDrawable(requireContext().getDrawable(R.drawable.choose_btn))
        }else{
            binding.chestBlue.ivChest.setImageDrawable(requireContext().getDrawable(R.drawable.chest_grey_buy))
            binding.chestBlue.chestStatus.setImageDrawable(requireContext().getDrawable(R.drawable.buy_btn))
        }
        //Red chest
        if (commonInfo.getChestStatus(3)) {
            binding.chestRed.ivChest.setImageDrawable(requireContext().getDrawable(R.drawable.chest_red))
            if (chosenChest==2)binding.chestRed.chestStatus.setImageDrawable(requireContext().getDrawable(R.drawable.choosen_btn))
            else binding.chestRed.chestStatus.setImageDrawable(requireContext().getDrawable(R.drawable.choose_btn))
        }else{
            binding.chestRed.ivChest.setImageDrawable(requireContext().getDrawable(R.drawable.chest_red_buy))
            binding.chestRed.chestStatus.setImageDrawable(requireContext().getDrawable(R.drawable.buy_btn))
        }
        //Green chest
        if (commonInfo.getChestStatus(3)) {
            binding.chestGreen.ivChest.setImageDrawable(requireContext().getDrawable(R.drawable.chest_green))
            if (chosenChest==2)binding.chestGreen.chestStatus.setImageDrawable(requireContext().getDrawable(R.drawable.choosen_btn))
            else binding.chestGreen.chestStatus.setImageDrawable(requireContext().getDrawable(R.drawable.choose_btn))
        }else{
            binding.chestGreen.ivChest.setImageDrawable(requireContext().getDrawable(R.drawable.chest_green_buy))
            binding.chestGreen.chestStatus.setImageDrawable(requireContext().getDrawable(R.drawable.buy_btn))
        }
    }

    private fun changeBtn(btnNumber:Int){
        if (commonInfo.getChestStatus(btnNumber)){
            commonInfo.buyChest(btnNumber)
        }else{
            commonInfo.choseChest(btnNumber)
        }
        initChest()
    }

}