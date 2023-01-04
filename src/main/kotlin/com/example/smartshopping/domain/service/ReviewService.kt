package com.example.smartshopping.domain.service

import com.example.smartshopping.common.SmartShoppingException
import com.example.smartshopping.domain.auth.UserContextHolder
import com.example.smartshopping.domain.repository.ReviewRepository
import com.example.smartshopping.domain.request.ReviewRequest
import com.example.smartshopping.domain.response.ReviewResponse
import com.example.smartshopping.entity.Review
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ReviewService @Autowired constructor(
    private val userService: UserService,
    private val productService: ProductService,
    private val reviewRepository: ReviewRepository,
    private val userContextHolder: UserContextHolder
) {

    fun getReviews(
        productId: Long?
    ): List<ReviewResponse> {
        val userCode = userContextHolder.userCode
        val condition = ReviewSearchCondition(userCode != null, productId != null)
        val reviews = when (condition) {
            USER_CODE_SEARCH -> reviewRepository.findByUserCodeOrderByIdDesc(userCode)
            PRODUCT_ID_SEARCH -> reviewRepository.findByProductIdOrderByIdDesc(productId)
            else -> throw IllegalArgumentException("리뷰 검색 조건 오류")
        }

        val responses = ArrayList<ReviewResponse>()

        for (review in reviews) {
            responses.add(review.toReviewResponse())
        }
        return responses
    }

    fun Review.toReviewResponse(): ReviewResponse {
        val reviewId = id ?: throw SmartShoppingException("리뷰 정보 없음")
        val nickName = userCode?.let {
            userService.find(it)?.nickName
        } ?: throw SmartShoppingException("사용자 정보 없음")
        val productName = productService.get(productId)?.name
            ?: throw SmartShoppingException("상품 정보 없음")

        return ReviewResponse(reviewId, purchaseId, nickName, productName, score, reviewText, updatedAt!!)
    }

    fun get(id: Long) = reviewRepository.findByIdOrNull(id)

    fun getByPurchaseId(purchaseId : Long?) = reviewRepository.findByPurchaseId(purchaseId)

    fun register(request: ReviewRequest) {
        userContextHolder.userCode?.let { userCode ->
            request.toReview(userCode)
                .run(::save)
        } ?: throw SmartShoppingException("사용자 정보 없음")
    }

    private fun save(review: Review) = reviewRepository.save(review)

    data class ReviewSearchCondition(
        val userCodeIsNotNull: Boolean,
        val productIdIsNotNull: Boolean
    )

    companion object {
        val USER_CODE_SEARCH = ReviewSearchCondition(userCodeIsNotNull = true, productIdIsNotNull = false)
        val PRODUCT_ID_SEARCH = ReviewSearchCondition(userCodeIsNotNull = false, productIdIsNotNull = true)
    }
}

private fun ReviewRequest.toReview(
    userCode: Long
) = Review(userCode, productId, purchaseId, score, reviewText)