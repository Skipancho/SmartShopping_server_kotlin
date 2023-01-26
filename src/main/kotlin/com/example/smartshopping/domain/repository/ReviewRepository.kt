package com.example.smartshopping.domain.repository

import com.example.smartshopping.entity.Review
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<Review, Long>{
    fun findByProductIdOrderByIdDesc(productId : Long?) : List<Review>
    fun findByUserCodeOrderByIdDesc(userCode : Long?) : List<Review>
    fun findByPurchaseId(purchaseId : Long?) : Review?
}