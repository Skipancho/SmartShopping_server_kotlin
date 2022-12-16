package com.example.smartshopping.domain.request

data class SignupRequest(
    val userId : String,
    val password : String,
    val nickName : String,
    val name : String
)
