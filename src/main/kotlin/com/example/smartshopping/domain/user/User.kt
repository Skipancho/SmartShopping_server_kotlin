package com.example.smartshopping.domain.user

import com.example.smartshopping.domain.jpa.BaseEntity
import javax.persistence.Entity

@Entity(name = "user")
class User (
    var userId : String,
    var password : String,
    var nickName : String,
    var name : String,
) : BaseEntity(){
}