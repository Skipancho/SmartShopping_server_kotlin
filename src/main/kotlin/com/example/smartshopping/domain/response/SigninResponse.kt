package com.example.smartshopping.domain.response

data class SigninResponse(
    val token : String,
    val refreshToken : String,
    val nickName : String,
    val userCode : Long,
    val userName : String
)
