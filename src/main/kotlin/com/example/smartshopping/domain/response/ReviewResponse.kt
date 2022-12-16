package com.example.smartshopping.domain.response

import java.util.*

data class ReviewResponse(
    val nickName : String,
    val productName : String,
    val score : Int,
    val reviewText : String,
    val date : Date
)
