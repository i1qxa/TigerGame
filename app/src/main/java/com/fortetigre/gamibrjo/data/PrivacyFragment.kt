package com.fortetigre.gamibrjo.data

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fortetigre.gamibrjo.R
import com.fortetigre.gamibrjo.databinding.FragmentPrivacyBinding

class PrivacyFragment : Fragment() {

    private val binding by lazy { FragmentPrivacyBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.tvPrivacy.text = Html.fromHtml(requireContext().getString(R.string.privacy))
    }
}