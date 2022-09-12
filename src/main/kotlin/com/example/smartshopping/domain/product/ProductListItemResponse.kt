package com.example.smartshopping.domain.product

import com.example.smartshopping.domain.product.image.ProductImage

data class ProductListItemResponse(
    val id: Long,
    val name: String,
    val description: String,
    val nPrice: Int?,
    val sPrice: Int,
    val status: String,
    val sellerId: Long,
    val imagePaths: List<String>
)

fun Product.toProductListItemResponse() = id?.let {
    ProductListItemResponse(
        it, name, description, nPrice, sPrice, status.name, userCode, images.map { it.toThumbs() }
    )
}

fun ProductImage.toThumbs(): String {
    /*val ext = path.takeLastWhile { it != '.' }
    val fileName = path.takeWhile { it != '.' }
    val thumbnailPath = "$fileName-thumb.$ext"
    return if (ext == "jpg") thumbnailPath else "$thumbnailPath.jpg"*/
    return path
}
