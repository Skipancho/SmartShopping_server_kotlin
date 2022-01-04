package com.example.smartshopping.domain.review

import com.example.smartshopping.domain.jpa.BaseEntity
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