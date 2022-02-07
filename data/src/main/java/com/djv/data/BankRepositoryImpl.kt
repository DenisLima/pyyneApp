package com.djv.data

import com.djv.data.bank1.Bank1AccountSource
import com.djv.data.bank2.Bank2AccountSource
import com.djv.domain.bank.BankRepository
import com.djv.domain.model.Transaction
import com.djv.domain.model.UserInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class BankRepositoryImpl(
    private val bank1AccountSource: Bank1AccountSource,
    private val bank2AccountSource: Bank2AccountSource
) : BankRepository {

    override suspend fun getUserInfo(
        accountId: Long,
        dateFrom: Date,
        dateTo: Date,
        bankId: Int
    ): Flow<UserInfo> {
        return flow {
            delay(3000)
            if (bankId == 1) {
                val userBalance = bank1AccountSource.getAccountBalance(accountId)
                val userCurrency = bank1AccountSource.getAccountCurrency(accountId)

                val transactions = bank1AccountSource.getTransactions(
                    accountId,
                    dateFrom,
                    dateTo
                )
                val transactionsMapped = mutableListOf<Transaction>()
                transactions.map {
                    transactionsMapped.add(
                        Transaction(
                            description = it.text,
                            type = it.type,
                            amount = it.amount
                        )
                    )
                }
                this.emit(
                    UserInfo(
                        balance = userBalance,
                        currency = userCurrency,
                        transactions = transactionsMapped
                    )
                )
            } else {
                val userBalance = bank2AccountSource.getBalance(accountId).balance
                val userCurrency = bank2AccountSource.getBalance(accountId).currency

                val transactions = bank2AccountSource.getTransactions(
                    accountId,
                    dateFrom,
                    dateTo
                )
                val transactionsMapped = mutableListOf<Transaction>()
                transactions.map {
                    transactionsMapped.add(
                        Transaction(
                            description = it.text,
                            type = it.type.ordinal,
                            amount = it.amount
                        )
                    )
                }
                this.emit(
                    UserInfo(
                        balance = userBalance,
                        currency = userCurrency,
                        transactions = transactionsMapped
                    )
                )
            }
        }
    }
}