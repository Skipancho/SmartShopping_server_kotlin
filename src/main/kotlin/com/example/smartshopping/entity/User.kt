package com.example.smartshopping.entity

import com.example.smartshopping.entity.base.BaseEntity
import javax.persistence.Entity

@Entity(name = "user")
class User (
    var userId : String,
    var password : String,
    var nickName : String,
    var name : String,
) : BaseEntity(){
}