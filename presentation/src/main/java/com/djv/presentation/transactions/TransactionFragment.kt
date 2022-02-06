package com.djv.presentation.transactions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.djv.domain.model.Transaction
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
        viewModel.initEvent(TransactionFragmentViewModel.TransactionViewEvent.FetchTransactions(bankId!!))
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
                is TransactionFragmentViewModel.TransactionViewState.UserBalance -> handleUserBalance(
                    viewState.balance
                )
                is TransactionFragmentViewModel.TransactionViewState.UserCurrency -> handleUserCurrency(
                    viewState.currency
                )
                is TransactionFragmentViewModel.TransactionViewState.UserTransactions -> handleTransactions(
                    viewState.transactions
                )
            }
        }
    }

    private fun handleTransactions(transactions: List<Transaction>) {
        binding.transactionRecycler.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = adapterList
            adapterList!!.setItems(transactions)
        }

        binding.shimmerLayout.visibility = View.GONE

        binding.divider.visibility = View.VISIBLE
        binding.transactionRecycler.visibility = View.VISIBLE
    }

    private fun handleUserCurrency(userCurrency: String) {
        binding.currencyTextview.text = userCurrency
        binding.currencyTextview.visibility = View.VISIBLE

        binding.mainShimmer.currencyShimmer.visibility = View.INVISIBLE
    }

    private fun handleUserBalance(userBalance: Double) {
        binding.balanceTextview.text = userBalance.toString()
        binding.icClose.visibility = View.VISIBLE
        binding.balanceTextview.visibility = View.VISIBLE

        binding.mainShimmer.iconShimmer.visibility = View.INVISIBLE
        binding.mainShimmer.balanceShimmer.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}