package com.example.smartshopping.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>{
    fun findByUserId(userId : String) : User?
    fun findByNickName(nickName : String) : User?
}