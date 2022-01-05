package com.example.smartshopping.domain.review

data class ReviewRequest(
    val productId : Long,
    val score : Int,
    val reviewText : String,
)
