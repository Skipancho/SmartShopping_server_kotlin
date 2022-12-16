package com.example.smartshopping.entity.base

enum class ProductStatus (private val status: String) {
    SELLABLE("판매중"),
    SOLD_OUT("품절")
}