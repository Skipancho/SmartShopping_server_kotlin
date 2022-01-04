package com.example.smartshopping.domain.review

import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<Review, Long>{
    fun findByProductIdOrderByIdDesc(productId : Long?) : List<Review>
    fun findByUserCodeOrderByIdDesc(userCode : Long?) : List<Review>
}