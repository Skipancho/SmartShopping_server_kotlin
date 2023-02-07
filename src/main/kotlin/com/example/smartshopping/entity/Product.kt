package com.example.smartshopping.entity

import com.example.smartshopping.entity.base.ProductStatus
import com.example.smartshopping.entity.base.BaseEntity
import javax.persistence.*

@Entity(name = "product")
class Product(
    @Column(length = 40)
    var name : String,
    @Column(length = 500)
    var description : String,
    var nPrice : Int?,
    var sPrice : Int,
    var categoryId : Int,
    @Enumerated(EnumType.STRING)
    var status: ProductStatus,
    @OneToMany
    @JoinColumn(name = "productId")
    var images : MutableList<ProductImage>,
    var userCode : Long,
    var barcode : Long
): BaseEntity() {
    companion object {
        val categoryMap = hashMapOf<Int, String>(
            0 to "식품",
            1 to "가전 제품",
            2 to "도서",
            3 to "뷰티",
            4 to "4번 항목",
            5 to "5번 항목",
            6 to "6번 항목",
        )
    }
}