package com.example.smartshopping.domain.product

import com.example.smartshopping.domain.jpa.BaseEntity
import javax.persistence.*

@Entity(name = "product")
class Product(
    @Column(length = 40)
    var name : String,
    @Column(length = 500)
    var description : String,
    var price : Int,
    var categoryId : Int,
    @Enumerated(EnumType.STRING)
    var status: ProductStatus,
    @OneToMany
    @JoinColumn(name = "productId")
    var images : MutableList<ProductImage>,
    var userCode : Long
): BaseEntity() {
}