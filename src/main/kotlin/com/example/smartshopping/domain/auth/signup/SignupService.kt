package com.example.smartshopping.domain.auth.signup

import com.example.smartshopping.common.SmartShoppingException
import com.example.smartshopping.domain.user.User
import com.example.smartshopping.domain.user.UserRepository
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SignupService @Autowired constructor(
    private val userRepository: UserRepository
){

    fun signup(signupRequest: SignupRequest){
        validateRequest(signupRequest)
        registerUser(signupRequest)
    }

    fun checkDuplicatesId(userId : String) =
        userRepository.findByUserId(userId)?.let{
            throw SmartShoppingException("이미 사용중인 아이디입니다.")
        }

    fun checkDuplicatesNickName(nickName: String) =
        userRepository.findByUserId(nickName)?.let{
            throw SmartShoppingException("이미 사용중인 닉네임입니다.")
        }

    private fun validateRequest(signupRequest: SignupRequest){
        validateNickName(signupRequest.nickName)
        validatePassword(signupRequest.password)
    }


    private fun validateNickName(nickName : String){
        if(nickName.trim().length !in 2..20)
            throw SmartShoppingException("닉네임은 2자 이상 20자 이하여야 합니다.")
    }

    private fun validatePassword(password: String){
        if(password.trim().length !in 8..20)
            throw SmartShoppingException("비밀번호는 공백 제외 8자 이상 20자 이하여야 합니다.")
    }

    private fun registerUser(signupRequest: SignupRequest)=
        with(signupRequest){
            val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())
            val user = User(userId,hashedPassword,nickName, name)
            userRepository.save(user)
        }
}