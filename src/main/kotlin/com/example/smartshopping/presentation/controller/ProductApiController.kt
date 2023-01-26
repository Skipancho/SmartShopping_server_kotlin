package com.example.smartshopping.presentation.controller

import com.example.smartshopping.common.ApiResponse
import com.example.smartshopping.common.SmartShoppingException
import com.example.smartshopping.domain.request.ProductRegistrationRequest
import com.example.smartshopping.domain.request.ReviewRequest
import com.example.smartshopping.domain.response.toProductListItemResponse
import com.example.smartshopping.domain.response.toProductResponse
import com.example.smartshopping.domain.service.ProductImageService
import com.example.smartshopping.domain.service.ProductRegistrationService
import com.example.smartshopping.domain.service.ProductService
import com.example.smartshopping.domain.service.ReviewService
import com.example.smartshopping.entity.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1")
class ProductApiController @Autowired constructor(
    private val productService : ProductService,
    private val productRegistrationService: ProductRegistrationService,
    private val productImageService: ProductImageService,
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

    @PostMapping("/products")
    fun register(
        @RequestBody request : ProductRegistrationRequest
    ) = ApiResponse.ok(
        productRegistrationService.register(request)
    )

    @PostMapping("/image")
    fun uploadImage(
        @RequestBody imageFile : MultipartFile
    ) = ApiResponse.ok(
        productImageService.uploadImage(imageFile)
    )

    @GetMapping("/products/{id}")
    fun get(@PathVariable id : Long) = productService.get(id)?.let{
        ApiResponse.ok(it.toProductResponse())
    } ?: throw SmartShoppingException("상품 정보를 찾을 수 없습니다.")

    @GetMapping("/product/{barcode}")
    fun getProductByBarcode(@PathVariable barcode : Long) = productService.getByBarcode(barcode)?.let {
        ApiResponse.ok(it.toProductResponse())
    } ?: throw SmartShoppingException("상품 정보를 찾을 수 없습니다.")
}