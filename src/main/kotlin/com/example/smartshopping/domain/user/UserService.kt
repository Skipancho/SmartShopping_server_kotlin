package com.example.smartshopping.domain.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository
){
    fun find(userCode : Long) = userRepository.findByIdOrNull(userCode)
}