package com.example.smartshopping.entity

import com.example.smartshopping.entity.base.BaseEntity
import javax.persistence.Entity

@Entity(name = "product_image")
class ProductImage(
    var path : String,
    var productId : Long? = null
) : BaseEntity(){
}