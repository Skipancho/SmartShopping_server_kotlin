package com.example.smartshopping.domain.request

data class ReviewRequest(
    val productId : Long,
    val purchaseId : Long,
    val score : Int,
    val reviewText : String,
)
