package com.djv.presentation.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djv.domain.bank1.BankUseCases
import com.djv.domain.model.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TransactionFragmentViewModel(
    private val bankUseCases: BankUseCases
) : ViewModel() {

    private val viewState = MutableLiveData<TransactionViewState>()
    fun getViewState(): LiveData<TransactionViewState> = viewState

    fun initEvent(viewEvent: TransactionViewEvent) {
        when (viewEvent) {
            is TransactionViewEvent.FetchTransactions -> {
                getBankUserBalance(MockAccountId.ACCOUNT_ID_BANK_1, viewEvent.bankId)
                getBankUserCurrency(MockAccountId.ACCOUNT_ID_BANK_1, viewEvent.bankId)
                getTransactions(
                    MockAccountId.ACCOUNT_ID_BANK_1,
                    MockAccountId.DATE_FROM,
                    MockAccountId.DATE_FROM,
                    viewEvent.bankId
                )
            }
        }
    }

    private fun getBankUserBalance(accountId: Long, bankId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(3000)
            bankUseCases.getBank1Balance(accountId, bankId).collect {
                viewState.postValue(TransactionViewState.UserBalance(it))
            }
        }
    }

    private fun getBankUserCurrency(accountId: Long, bankId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(4000)
            bankUseCases.getCurrency(accountId, bankId).collect {
                viewState.postValue(TransactionViewState.UserCurrency(it))
            }
        }
    }

    private fun getTransactions(accountId: Long, dateFrom: Date, dateTo: Date, bankId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(5000)
            bankUseCases.getTransactions(accountId, dateFrom, dateTo, bankId).collect {
                viewState.postValue(TransactionViewState.UserTransactions(it))
            }
        }
    }

    sealed class TransactionViewEvent {
        data class FetchTransactions(val bankId: Int) : TransactionViewEvent()
    }

    sealed class TransactionViewState {
        data class UserBalance(val balance: Double) : TransactionViewState()
        data class UserCurrency(val currency: String) : TransactionViewState()
        data class UserTransactions(val transactions: List<Transaction>) : TransactionViewState()
    }

}