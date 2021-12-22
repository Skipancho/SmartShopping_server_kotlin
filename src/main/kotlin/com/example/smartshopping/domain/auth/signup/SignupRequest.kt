package com.example.smartshopping.domain.auth.signup

data class SignupRequest(
    val userId : String,
    val password : String,
    val nickName : String,
    val name : String
)
