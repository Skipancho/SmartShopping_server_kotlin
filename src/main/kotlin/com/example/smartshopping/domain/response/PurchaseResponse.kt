package com.example.smartshopping.domain.response

import com.example.smartshopping.common.SmartShoppingException
import com.example.smartshopping.entity.Product
import com.example.smartshopping.entity.PurchaseRecord

data class PurchaseResponse(
    val id : Long,
    val category : String,
    val productId : Long,
    val productName : String,
    val price : Int,
    val amount : Int
)

fun PurchaseRecord.toPurchaseResponse() = id?.let {
    PurchaseResponse(
        it, Product.categoryMap[categoryId] ?: "기타", productId, productName, price, amount
    )
} ?: throw SmartShoppingException("구매 정보를 찾을 수 없습니다.")