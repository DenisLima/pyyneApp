package com.djv.domain.model

data class UserInfo(
    val balance: Double,
    val currency: String,
    val transactions: List<Transaction>
)
