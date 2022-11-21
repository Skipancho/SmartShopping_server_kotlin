package com.example.smartshopping.domain.product.registration

data class ProductRegistrationRequest(
    val name : String,
    val description : String,
    val nPrice : Int?,
    val sPrice : Int,
    val categoryId : Int,
    val imageIds : List<Long?>,
    val barcode : Long
)
