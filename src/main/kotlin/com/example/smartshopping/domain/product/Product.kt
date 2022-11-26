package com.example.smartshopping.domain.product

import com.example.smartshopping.domain.jpa.BaseEntity
import com.example.smartshopping.domain.product.image.ProductImage
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
            0 to "0번 항목",
            1 to "1번 항목",
            2 to "2번 항목",
            3 to "3번 항목",
            4 to "4번 항목",
            5 to "5번 항목",
            6 to "6번 항목",
        )
    }
}