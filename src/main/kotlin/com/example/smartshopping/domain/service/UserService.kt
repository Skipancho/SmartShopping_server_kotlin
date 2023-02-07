package com.example.smartshopping.domain.service

import com.example.smartshopping.domain.auth.UserContextHolder
import com.example.smartshopping.domain.repository.UserRepository
import com.example.smartshopping.domain.request.SignupRequest
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository,
    private val userContextHolder: UserContextHolder
){
    fun find(userCode : Long) = userRepository.findByIdOrNull(userCode)

    fun updateUserInfo(request: SignupRequest) {
        val user = userContextHolder.userCode?.let { find(it) }
        user?.run {
            name = request.name
            nickName = request.nickName
            val hashedPassword = BCrypt.hashpw(request.password, BCrypt.gensalt())
            password = hashedPassword
            userRepository.save(this)
        }
    }

    fun deleteUserInfo() {
        val user = userContextHolder.userCode?.let { find(it) }
        user?.run(userRepository::delete)
    }
}