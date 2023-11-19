package com.fortetigre.gamibrjo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fortetigre.gamibrjo.databinding.FragmentGameResultBinding

private const val GAME_TIME = "game_time"
private const val GAME_RESULT = "game_result"

class GameResultFragment : Fragment() {
    private var gameTime: String? = null
    private var gameResult: Int? = null

    private val binding by lazy { FragmentGameResultBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            gameTime = it.getString(GAME_TIME)
            gameResult = it.getInt(GAME_RESULT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupResult()
        setupBtnClickListeners()
    }

    private fun setupBtnClickListeners(){
        binding.btnHome.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainContainer, StartGameFragment())
                commit()
            }
        }
        binding.btnReplay.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainContainer, GameFragment())
                commit()
            }
        }
    }

    private fun setupResult(){
        if (gameResult!=null && gameTime!=null){
            if (gameResult!!>0)setupWinRes()
            else setupLoseRes()
        }else{
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainContainer, StartGameFragment())
                commit()
            }
        }
        binding.tvTimeValue.text = gameTime
        binding.tvRewardValue.text = gameResult?.toString()?:"0"
    }

    private fun setupWinRes(){
        binding.tvTopHeader1.text = requireContext().getString(R.string.lvl)
        binding.tvTopHeader2.text = requireContext().getString(R.string.complete)
    }

    private fun setupLoseRes(){
        binding.tvTopHeader1.text = requireContext().getString(R.string.you)
        binding.tvTopHeader2.text = requireContext().getString(R.string.lose)
    }

    companion object {
        @JvmStatic
        fun newInstance(gameTime: String, gameResult: Int) =
            GameResultFragment().apply {
                arguments = Bundle().apply {
                    putString(GAME_TIME, gameTime)
                    putInt(GAME_RESULT, gameResult)
                }
            }
    }
}