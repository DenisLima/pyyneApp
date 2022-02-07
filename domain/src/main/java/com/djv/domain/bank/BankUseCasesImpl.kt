package com.djv.domain.bank

import com.djv.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow
import java.util.*

class BankUseCasesImpl(
    private val bankRepository: BankRepository
): BankUseCases {

    override suspend fun getUserInfo(
        accountId: Long,
        dateFrom: Date,
        dateTo: Date,
        bankId: Int
    ): Flow<UserInfo> {
        return bankRepository.getUserInfo(
            accountId, dateFrom, dateTo, bankId
        )
    }
}