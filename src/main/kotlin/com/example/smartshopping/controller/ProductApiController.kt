package com.example.smartshopping.controller

import com.example.smartshopping.common.ApiResponse
import com.example.smartshopping.common.SmartShoppingException
import com.example.smartshopping.domain.product.Product
import com.example.smartshopping.domain.product.ProductService
import com.example.smartshopping.domain.product.registration.ProductImageService
import com.example.smartshopping.domain.product.registration.ProductRegistrationRequest
import com.example.smartshopping.domain.product.registration.ProductRegistrationService
import com.example.smartshopping.domain.product.toProductListItemResponse
import com.example.smartshopping.domain.product.toProductResponse
import com.example.smartshopping.domain.review.ReviewRequest
import com.example.smartshopping.domain.review.ReviewService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1")
class ProductApiController @Autowired constructor(
    private val productService : ProductService,
    private val productRegistrationService: ProductRegistrationService,
    private val productImageService: ProductImageService,
    private val reviewService: ReviewService
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

    @GetMapping("/reviews")
    fun getReview(
        @RequestParam(required = false) userCode : Long?,
        @RequestParam(required = false) productId : Long?
    ) = reviewService
        .getReviews(userCode, productId)
        .let {ApiResponse.ok(it)}

    @PostMapping("/review")
    fun writeReview(
        @RequestBody reviewRequest: ReviewRequest
    ) = ApiResponse.ok(reviewService.register(reviewRequest))

}