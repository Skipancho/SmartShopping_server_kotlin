package com.example.smartshopping.presentation.controller

import com.example.smartshopping.common.ApiResponse
import com.example.smartshopping.domain.request.ReviewRequest
import com.example.smartshopping.domain.service.ReviewService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ReviewApiController @Autowired constructor(
    private val reviewService: ReviewService
) {
    @GetMapping("/reviews")
    fun getReview(
        @RequestParam(required = false) productId : Long?
    ) = reviewService
        .getReviews(productId)
        .let { ApiResponse.ok(it)}

    @PostMapping("/review")
    fun writeReview(
        @RequestBody reviewRequest: ReviewRequest
    ) = ApiResponse.ok(reviewService.register(reviewRequest))
}