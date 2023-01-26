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
    fun getReviews(
        @RequestParam(required = false) productId: Long?
    ) = reviewService
        .getReviews(productId)
        .let { ApiResponse.ok(it) }

    @GetMapping("/review")
    fun getReview(
        @RequestParam reviewId : Long
    ) = reviewService
        .get(reviewId)
        .let { ApiResponse.ok(it) }

    @PostMapping("/review")
    fun writeReview(
        @RequestBody reviewRequest: ReviewRequest
    ) = ApiResponse.ok(reviewService.register(reviewRequest))

    @PutMapping("/review")
    fun updateReview(
        @RequestBody reviewRequest: ReviewRequest
    ) = ApiResponse.ok(reviewService.update(reviewRequest))

    @DeleteMapping("/review")
    fun deleteReview(
        @RequestParam reviewId: Long
    ) = ApiResponse.ok(reviewService.delete(reviewId))
}