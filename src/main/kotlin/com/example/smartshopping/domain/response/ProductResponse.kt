package com.example.smartshopping.domain.response

import com.example.smartshopping.common.SmartShoppingException
import com.example.smartshopping.entity.Product

data class ProductResponse(
    val id: Long,
    val name: String,
    val description: String,
    val nPrice: Int?,
    val sPrice: Int,
    val status: String,
    val sellerId: Long,
    val categoryId : Int,
    val imagePaths: List<String>
)

fun Product.toProductResponse() = id?.let {
    ProductResponse(
        it, name, description, nPrice, sPrice, status.name, userCode, categoryId,
        images.map { image ->  image.path }
    )
} ?: throw SmartShoppingException("상품 정보를 찾을 수 없습니다.")
