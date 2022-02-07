package com.djv.presentation.transactions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.djv.domain.model.UserInfo
import com.djv.presentation.databinding.FragmentHomeSecondBinding
import com.djv.presentation.home.HomeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransactionFragment : Fragment() {

    private var _binding: FragmentHomeSecondBinding? = null
    private var adapterList: TransactionAdapter? = null

    private val binding get() = _binding!!

    private val viewModel by viewModel<TransactionFragmentViewModel>()
    private val bankId by lazy {
        arguments?.getInt(HomeFragment.ARGS)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        prepareObservers()
        viewModel.initEvent(
            TransactionFragmentViewModel.TransactionViewEvent.FetchTransactions(
                bankId!!
            )
        )
        adapterList = TransactionAdapter(requireContext())
    }

    private fun initView() {
        binding.icClose.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun prepareObservers() {
        viewModel.getViewState().observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is TransactionFragmentViewModel.TransactionViewState.UserInfo -> handleUserInfo(
                    userInfo = viewState.userInfo
                )
            }
        }
    }

    private fun handleUserInfo(userInfo: UserInfo) {
        binding.transactionRecycler.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = adapterList
            adapterList!!.setItems(userInfo.transactions)
        }
        binding.currencyTextview.text = userInfo.currency
        binding.balanceTextview.text = userInfo.balance.toString()

        binding.shimmerLayout.visibility = View.GONE

        binding.divider.visibility = View.VISIBLE
        binding.transactionRecycler.visibility = View.VISIBLE
        binding.currencyTextview.visibility = View.VISIBLE
        binding.icClose.visibility = View.VISIBLE
        binding.balanceTextview.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}