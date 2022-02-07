package com.djv.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.djv.presentation.R
import com.djv.presentation.databinding.FragmentHomeFirstBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.firstButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundleOf(ARGS to BANK_1))
        }

        binding.secondButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundleOf(ARGS to BANK_2))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARGS = "bank_args"
        private const val BANK_1 = 1
        private const val BANK_2 = 2
    }
}