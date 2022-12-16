package com.example.smartshopping.domain.request

import com.example.smartshopping.entity.PurchaseRecord

data class PurchaseRequest(
    var productId: Long,
    var productName: String,
    var price: Int,
    var amount: Int,
    var categoryId: Int
)

fun PurchaseRequest.toPurchaseRecord(userCode: Long, year: Int, month: Int) = PurchaseRecord(
    userCode, productId, productName, price, amount, categoryId, year, month
)