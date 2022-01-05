package com.example.smartshopping.domain.review

import com.example.smartshopping.common.SmartShoppingException
import com.example.smartshopping.domain.product.ProductService
import com.example.smartshopping.domain.user.UserService
import java.util.*

data class ReviewResponse(
    val nickName : String,
    val productName : String,
    val score : Int,
    val reviewText : String,
    val date : Date
)
