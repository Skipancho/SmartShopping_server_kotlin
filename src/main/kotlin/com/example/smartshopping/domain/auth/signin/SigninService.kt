package com.example.smartshopping.domain.auth.signin

import com.example.smartshopping.common.SmartShoppingException
import com.example.smartshopping.domain.auth.JWTUtil
import com.example.smartshopping.domain.user.User
import com.example.smartshopping.domain.user.UserRepository
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalStateException

@Service
class SigninService @Autowired constructor(
    private val userRepository: UserRepository
) {

    fun signin(signinRequest: SigninRequest) : SigninResponse{
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
        userCode
    )
    } ?: throw IllegalStateException("userCode 없음.")
}