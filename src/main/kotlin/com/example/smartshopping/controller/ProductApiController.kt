package com.example.smartshopping.controller

import com.example.smartshopping.common.ApiResponse
import com.example.smartshopping.common.SmartShoppingException
import com.example.smartshopping.domain.product.Product
import com.example.smartshopping.domain.product.ProductService
import com.example.smartshopping.domain.product.toProductListItemResponse
import com.example.smartshopping.domain.product.toProductResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ProductApiController @Autowired constructor(
    private val productService : ProductService
){
    @GetMapping("/products")
    fun search(
        @RequestParam productId:Long,
        @RequestParam(required = false) categoryId : Int?,
        @RequestParam direction : String,
        @RequestParam(required = false) keyword : String?,
        @RequestParam(required = false) limit : Int?
    ) = productService
        .search(categoryId,productId,direction,keyword,limit ?: 10)
        .mapNotNull(Product :: toProductListItemResponse)
        .let { ApiResponse.ok(it) }


    @GetMapping("/products/{id}")
    fun get(@PathVariable id : Long) = productService.get(id)?.let{
        ApiResponse.ok(it.toProductResponse())
    } ?: throw SmartShoppingException("상품 정보를 찾을 수 없습니다.")
}