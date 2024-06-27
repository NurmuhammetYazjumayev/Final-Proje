package com.mustfaibra.roffu.models

data class VirtualCard(
    val cardHolder: String,
    val cardNumber: String,
    val expiryDate: String,
    val cvv: String
)
