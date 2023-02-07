package com.example.smartshopping.domain.repository

import com.example.smartshopping.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>{
    fun findByUserId(userId : String) : User?
    fun findByNickName(nickName : String) : User?
}