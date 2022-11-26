package com.example.smartshopping.domain.purchase

import com.example.smartshopping.domain.jpa.BaseEntity
import javax.persistence.Entity

@Entity(name = "purchase")
class PurchaseRecord(
    var userCode : Long,
    var productId : Long,
    var productName : String,
    var price : Int,
    var amount : Int,
    var categoryId : Int,
    var year : Int,
    var month : Int
) : BaseEntity() {
}