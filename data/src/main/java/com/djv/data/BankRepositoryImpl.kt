package com.djv.data

import com.djv.data.bank1.Bank1AccountSource
import com.djv.data.bank2.Bank2AccountSource
import com.djv.domain.bank1.BankRepository
import com.djv.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class BankRepositoryImpl(
    private val bank1AccountSource: Bank1AccountSource,
    private val bank2AccountSource: Bank2AccountSource
): BankRepository {

    override suspend fun getRepositoryBalance(accountId: Long, bankId: Int): Flow<Double> {
        return flow {
            if (bankId == 1) {
                val balance = bank1AccountSource.getAccountBalance(accountId)
                this.emit(balance)
            } else {
                val balance = bank2AccountSource.getBalance(accountId).balance
                this.emit(balance)
            }
        }
    }

    override suspend fun getCurrencyBalance(accountId: Long, bankId: Int): Flow<String> {
        return flow {
            if (bankId == 1) {
                val currency = bank1AccountSource.getAccountCurrency(accountId)
                this.emit(currency)
            } else {
                val currency = bank2AccountSource.getBalance(accountId).currency
                this.emit(currency)
            }
        }
    }

    override suspend fun getTransactions(
        accountId: Long,
        dateFrom: Date,
        dateTo: Date,
        bankId: Int
    ): Flow<List<Transaction>> {
        return flow {
            if (bankId == 1) {
                val transactions = bank1AccountSource.getTransactions(
                    accountId,
                    dateFrom,
                    dateTo
                )
                val finalList = mutableListOf<Transaction>()
                transactions.map {
                    finalList.add(
                        Transaction(
                            description = it.text,
                            type = it.type,
                            amount = it.amount
                        )
                    )
                }
                this.emit(finalList)
            } else {
                val transactions = bank2AccountSource.getTransactions(
                    accountId,
                    dateFrom,
                    dateTo
                )
                val finalList = mutableListOf<Transaction>()
                transactions.map {
                    finalList.add(
                        Transaction(
                            description = it.text,
                            type = it.type.ordinal,
                            amount = it.amount
                        )
                    )
                }
                this.emit(finalList)
            }
        }
    }
}