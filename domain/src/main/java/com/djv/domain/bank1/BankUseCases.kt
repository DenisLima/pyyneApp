package com.djv.domain.bank1

import com.djv.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import java.util.*

interface BankUseCases {
    suspend fun getBank1Balance(accountId: Long, bankId: Int): Flow<Double>
    suspend fun getCurrency(accountId: Long, bankId: Int): Flow<String>
    suspend fun getTransactions(accountId: Long, dateFrom: Date, dateTo: Date, bankId: Int): Flow<List<Transaction>>
}