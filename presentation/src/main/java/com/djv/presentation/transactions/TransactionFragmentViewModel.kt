package com.djv.presentation.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djv.domain.bank.BankUseCases
import com.djv.domain.bank.MockAccountId
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class TransactionFragmentViewModel(
    private val bankUseCases: BankUseCases
) : ViewModel() {

    private val viewState = MutableLiveData<TransactionViewState>()
    fun getViewState(): LiveData<TransactionViewState> = viewState

    fun initEvent(viewEvent: TransactionViewEvent) {
        when (viewEvent) {
            is TransactionViewEvent.FetchTransactions -> {
                getUserInfo(
                    MockAccountId.ACCOUNT_ID_BANK_1,
                    MockAccountId.DATE_FROM,
                    MockAccountId.DATE_FROM,
                    viewEvent.bankId
                )
            }
        }
    }

    private fun getUserInfo(accountId: Long, dateFrom: Date, dateTo: Date, bankId: Int) {
        viewModelScope.launch {
            bankUseCases.getUserInfo(accountId, dateFrom, dateTo, bankId).collect {
                viewState.postValue(TransactionViewState.UserInfo(it))
            }
        }
    }

    sealed class TransactionViewEvent {
        data class FetchTransactions(val bankId: Int) : TransactionViewEvent()
    }

    sealed class TransactionViewState {
        data class UserInfo(val userInfo: com.djv.domain.model.UserInfo): TransactionViewState()
    }

}