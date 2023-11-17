package com.fortetigre.gamibrjo

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.fortetigre.gamibrjo.data.CommonInfo
import com.fortetigre.gamibrjo.data.db.AppDatabase
import com.fortetigre.gamibrjo.data.db.BalanceDB
import com.fortetigre.gamibrjo.data.db.ChestDB
import com.fortetigre.gamibrjo.databinding.FragmentShopGameBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShopGameFragment : Fragment() {

    private val binding by lazy { FragmentShopGameBinding.inflate(layoutInflater) }
    private val tvBalance by lazy { binding.balance.tvBalance }
    private val commonInfo by lazy { CommonInfo }
    private val balanceDao by lazy { AppDatabase.getInstance(requireActivity().application).BalanceDao() }
    private val chestDao by lazy { AppDatabase.getInstance(requireActivity().application).ChestDao() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeBalance()
        observeChestList()
        setupBtnClickListeners()
    }

    private fun observeBalance() {
        makeGradientText()
        balanceDao.getBalanceLD().observe(viewLifecycleOwner){
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

        tvBalance.paint.setShader(textShader)
    }

    private fun setupBtnClickListeners() {
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun observeChestList() {
        chestDao.getLDChestItems().observe(viewLifecycleOwner){ chestList ->
            chestList.map { chest ->
                when(chest.name){
                    "GREY" -> {
                        with(binding.chestGrey){
                            ivChest.setImageDrawable(requireContext().getDrawable(chest.getChestImgId()))
                            chestStatus.setImageDrawable(requireActivity().getDrawable(chest.getBtnImgId()))
                            root.setOnClickListener { updateChestStatus(chest) }
                        }
                    }
                    "BLUE" -> {
                        with(binding.chestBlue){
                            ivChest.setImageDrawable(requireContext().getDrawable(chest.getChestImgId()))
                            chestStatus.setImageDrawable(requireActivity().getDrawable(chest.getBtnImgId()))
                            root.setOnClickListener { updateChestStatus(chest) }
                        }
                    }
                    "RED" -> {
                        with(binding.chestRed){
                            ivChest.setImageDrawable(requireContext().getDrawable(chest.getChestImgId()))
                            chestStatus.setImageDrawable(requireActivity().getDrawable(chest.getBtnImgId()))
                            root.setOnClickListener { updateChestStatus(chest) }
                        }
                    }
                    "GREEN" -> {
                        with(binding.chestGreen){
                            ivChest.setImageDrawable(requireContext().getDrawable(chest.getChestImgId()))
                            chestStatus.setImageDrawable(requireActivity().getDrawable(chest.getBtnImgId()))
                            root.setOnClickListener { updateChestStatus(chest) }
                        }
                    }
                }
            }
        }
    }

    private fun updateChestStatus(chestDB: ChestDB){
        lifecycleScope.launch(Dispatchers.IO) {
        var newChestDB = chestDB.btnClicked()
        if (newChestDB?.isBuying==true && chestDB.isBuying != newChestDB.isBuying){
            val newBalanceValue = balanceDao.getCurrentBalanceValue() - chestDB.price
            if (newBalanceValue>0){
                balanceDao.decreaseBalance(BalanceDB(1, newBalanceValue))
            }else{
                newChestDB= chestDB
                withContext(Dispatchers.Main){
                    Toast.makeText(requireContext(), "You're got not enough coin's",Toast.LENGTH_LONG).show()
                }
            }
        }
            if (newChestDB!=null){
                chestDao.addChest(newChestDB)
                if (newChestDB.isChosen){
                    val chestListToCancelChosen = chestDao.getChestListToCancelChosen(newChestDB.name)
                    chestListToCancelChosen.map {
                        chestDao.addChest(it.cancelChosen())
                    }
                }
            }
        }
    }

}