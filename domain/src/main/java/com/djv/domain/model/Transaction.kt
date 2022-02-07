package com.djv.domain.model

data class Transaction(
    val description: String,
    val type: Int,
    val amount: Double
)
