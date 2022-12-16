package com.example.smartshopping.entity

import com.example.smartshopping.entity.base.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "review")
class Review(
    var userCode : Long? = null,
    var productId : Long? = null,
    var score : Int,
    @Column(length = 500)
    var reviewText : String,
) : BaseEntity(){
}