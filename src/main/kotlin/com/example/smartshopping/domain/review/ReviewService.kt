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
    private val reviewRepository: ReviewRepository,
    private val userContextHolder: UserContextHolder
) {

    fun getReviews(
        userCode : Long?,
        productId : Long?
    ): List<Review> {
        val condition = ReviewSearchCondition(userCode != null, productId != null)

        return when(condition){
            USER_CODE_SEARCH -> reviewRepository.findByUserCodeOrderByIdDesc(userCode)
            PRODUCT_ID_SEARCH -> reviewRepository.findByProductIdOrderByIdDesc(productId)
            else ->throw IllegalArgumentException("리뷰 검색 조건 오류")
        }
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