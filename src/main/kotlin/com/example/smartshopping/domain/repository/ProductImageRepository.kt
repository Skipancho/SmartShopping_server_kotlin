package com.example.smartshopping.domain.repository

import com.example.smartshopping.entity.ProductImage
import org.springframework.data.jpa.repository.JpaRepository

interface ProductImageRepository : JpaRepository<ProductImage, Long>{
    fun findByIdIn(imageIds : List<Long>) : MutableList<ProductImage>
}