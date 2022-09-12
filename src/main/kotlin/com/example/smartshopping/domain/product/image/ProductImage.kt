package com.example.smartshopping.domain.product.image

import com.example.smartshopping.domain.jpa.BaseEntity
import javax.persistence.Entity

@Entity(name = "product_image")
class ProductImage(
    var path : String,
    var productId : Long? = null
) : BaseEntity(){
}