package com.example.smartshopping.domain.product

import com.example.smartshopping.common.SmartShoppingException

data class ProductResponse(
    val id: Long,
    val name: String,
    val description: String,
    val nPrice: Int?,
    val sPrice: Int,
    val status: String,
    val sellerId: Long,
    val imagePaths: List<String>
)

fun Product.toProductResponse() = id?.let {
    ProductResponse(
        it, name, description, nPrice, sPrice, status.name, userCode,
        images.map { image ->  image.path }
    )
} ?: throw SmartShoppingException("상품 정보를 찾을 수 없습니다.")
