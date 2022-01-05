package com.example.smartshopping.domain.review

import com.example.smartshopping.common.SmartShoppingException
import com.example.smartshopping.domain.auth.UserContextHolder
import com.example.smartshopping.domain.product.ProductService
import com.example.smartshopping.domain.user.UserService
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
        userCode : Long?,
        productId : Long?
    ): List<ReviewResponse> {
        val condition = ReviewSearchCondition(userCode != null, productId != null)
        val reviews = when(condition){
            USER_CODE_SEARCH -> reviewRepository.findByUserCodeOrderByIdDesc(userCode)
            PRODUCT_ID_SEARCH -> reviewRepository.findByProductIdOrderByIdDesc(productId)
            else ->throw IllegalArgumentException("리뷰 검색 조건 오류")
        }

        val responses = ArrayList<ReviewResponse>()

        for (review in reviews){
            val nickName = review.userCode?.let {
                userService.find(it)?.nickName
            } ?: ""
            val productname = review.productId?.let{
                productService.get(it)?.name
            } ?: ""

            responses.add(ReviewResponse(nickName,productname,review.score,review.reviewText,review.updatedAt!!))
        }
        return responses
    }

    fun Review.toReviewResponse(): ReviewResponse {
        val nickName = userCode?.let {
            userService.find(it)?.nickName
        } ?: throw SmartShoppingException("사용자 정보 없음")
        val productName = productId?.let {
            productService.get(it)?.name
        } ?: throw SmartShoppingException("상품 정보 없음")

        return ReviewResponse(nickName,productName,score,reviewText,updatedAt!!)
    }

    fun get(id : Long) = reviewRepository.findByIdOrNull(id)

    fun register(request: ReviewRequest) =
        userContextHolder.userCode?.let { userCode ->
            request.toReview(userCode)
                .run(::save)
        } ?: throw SmartShoppingException("사용자 정보 없음")

    private fun save(review: Review) = reviewRepository.save(review)

    data class ReviewSearchCondition(
        val userCodeIsNotNull : Boolean,
        val productIdIsNotNull : Boolean
    )

    companion object{
        val USER_CODE_SEARCH = ReviewSearchCondition(true, false)
        val PRODUCT_ID_SEARCH = ReviewSearchCondition(false, true)
    }
}

private fun ReviewRequest.toReview(
    userCode: Long
) = Review(userCode, productId, score, reviewText)