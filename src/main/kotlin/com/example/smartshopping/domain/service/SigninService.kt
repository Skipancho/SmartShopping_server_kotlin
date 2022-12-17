package com.example.smartshopping.domain.service

import com.example.smartshopping.common.SmartShoppingException
import com.example.smartshopping.domain.auth.JWTUtil
import com.example.smartshopping.domain.repository.UserRepository
import com.example.smartshopping.domain.request.SigninRequest
import com.example.smartshopping.domain.response.SigninResponse
import com.example.smartshopping.entity.User
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SigninService @Autowired constructor(
    private val userRepository: UserRepository
) {

    fun signin(signinRequest: SigninRequest) : SigninResponse {
        val user = userRepository
            .findByUserId(signinRequest.userId.lowercase())
            ?:throw SmartShoppingException("로그인 정보를 확인해주세요.")

        if (isNotValidPassword(signinRequest.password, user.password)){
            throw SmartShoppingException("로그인 정보를 확인해주세요.")
        }

        return responseWithTokens(user)
    }


    private fun isNotValidPassword(
        plain: String,
        hashed: String
    ) = BCrypt.checkpw(plain,hashed).not()

    private fun responseWithTokens(user: User) = user.id?.let{
            userCode -> SigninResponse(
        JWTUtil.createToken(user.userId),
        JWTUtil.createRefreshToken(user.userId),
        user.nickName,
        userCode,
        user.name
    )
    } ?: throw IllegalStateException("userCode 없음.")
}