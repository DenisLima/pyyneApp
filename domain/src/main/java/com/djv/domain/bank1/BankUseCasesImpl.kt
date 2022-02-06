package com.djv.domain.bank1

import com.djv.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import java.util.*

class BankUseCasesImpl(
    private val bankRepository: BankRepository
): BankUseCases {

    override suspend fun getBank1Balance(accountId: Long, bankId: Int): Flow<Double> {
        return bankRepository.getRepositoryBalance(accountId, bankId)
    }

    override suspend fun getCurrency(accountId: Long, bankId: Int): Flow<String> {
        return bankRepository.getCurrencyBalance(accountId, bankId)
    }

    override suspend fun getTransactions(
        accountId: Long,
        dateFrom: Date,
        dateTo: Date,
        bankId: Int
    ): Flow<List<Transaction>> {
        return bankRepository.getTransactions(
            accountId,
            dateFrom,
            dateTo,
            bankId
        )
    }
}