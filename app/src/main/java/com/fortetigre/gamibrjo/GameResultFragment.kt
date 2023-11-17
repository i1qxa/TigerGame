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
    private var gameTime: Int? = null
    private var gameResult: Int? = null

    private val binding by lazy { FragmentGameResultBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            gameTime = it.getInt(GAME_TIME)
            gameResult = it.getInt(GAME_RESULT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(gameTime: Int, gameResult: Int) =
            GameResultFragment().apply {
                arguments = Bundle().apply {
                    putInt(GAME_TIME, gameTime)
                    putInt(GAME_RESULT, gameResult)
                }
            }
    }
}