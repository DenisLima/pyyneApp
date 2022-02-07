package com.djv.domain.bank

import com.djv.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow
import java.util.*

interface BankUseCases {
    suspend fun getUserInfo(accountId: Long, dateFrom: Date, dateTo: Date, bankId: Int): Flow<UserInfo>
}