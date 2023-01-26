package com.example.smartshopping.domain.response

import java.util.*

data class ReviewResponse(
    val id: Long,
    val purchaseId: Long,
    val nickName: String,
    val productId: Long,
    val score: Int,
    val reviewText: String,
    val date: Date
)
