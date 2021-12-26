package com.example.smartshopping.controller

import com.example.smartshopping.common.ApiResponse
import com.example.smartshopping.domain.auth.JWTUtil
import com.example.smartshopping.domain.auth.UserContextHolder
import com.example.smartshopping.interceptor.TokenValidationInterceptor
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1")
class TokenApiController (
    private val userContextHolder: UserContextHolder
){
    @PostMapping("/refresh_token")
    fun refreshToken(
        @RequestParam("grant_type") grantType : String
    ): ApiResponse {
        if (grantType != TokenValidationInterceptor.GRANT_TYPE_REFRESH){
            throw IllegalArgumentException("grant_type 없음")
        }

        return userContextHolder.userId?.let{
            ApiResponse.ok(JWTUtil.createToken(it))
        } ?: throw IllegalArgumentException("사용자 정보 없음")
    }
}