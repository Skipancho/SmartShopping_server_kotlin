package com.example.smartshopping.domain.auth

import com.example.smartshopping.domain.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserContextHolder @Autowired constructor(
    private val userRepository: UserRepository
){
    private var userHolder = ThreadLocal.withInitial {
        UserHolder()
    }

    val userCode : Long? get() = userHolder.get().userCode
    val userId : String? get() = userHolder.get().userId
    val nickName : String? get() = userHolder.get().nickName

    fun set(userId : String) = userRepository
        .findByUserId(userId)?.let { user ->
            this.userHolder.get().apply {
                this.userCode = user.id
                this.userId = user.userId
                this.nickName = user.nickName
            }.run(userHolder::set)
        }

    fun clear() {
        userHolder.remove()
    }

    class UserHolder{
        var userCode : Long? = null
        var userId : String? = null
        var nickName : String? = null
    }
}