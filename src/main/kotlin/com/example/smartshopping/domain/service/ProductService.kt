package com.example.smartshopping.domain.service

import com.example.smartshopping.domain.repository.ProductRepository
import com.example.smartshopping.entity.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductService @Autowired constructor(
    private val productRepository: ProductRepository
){
    fun search(
        categoryId : Int?,
        productId : Long,
        direction : String,
        keyword : String?,
        limit : Int
    ): List<Product>{
        val pageable = PageRequest.of(0, limit)
        val condition = ProductSearchCondition(
            categoryId != null,
            direction,
            keyword != null
        )

        return when(condition){
            NEXT_IN_SEARCH -> productRepository
                .findByIdLessThanAndNameLikeOrderByIdDesc(
                    productId,"%$keyword%",pageable
                )
            PREV_IN_SEARCH -> productRepository
                .findByIdGreaterThanAndNameLikeOrderByIdDesc(
                    productId,"%$keyword%",pageable
                )
            NEXT_IN_CATEGORY -> productRepository
                .findByCategoryIdAndIdLessThanOrderByIdDesc(
                    categoryId,productId,pageable
                )
            PREV_IN_CATEGORY -> productRepository
                .findByCategoryIdAndIdGreaterThanOrderByIdDesc(
                    categoryId,productId,pageable
                )
            DEFAULT_SEARCH -> productRepository
                .findByIdLessThanOrderByIdDesc(
                    productId,pageable
                )
            PREV_DEFAULT_SEARCH -> productRepository
                .findByIdGreaterThanOrderByIdDesc(
                    productId,pageable
                )
            else -> throw IllegalArgumentException("상품 검색 조건 오류")

        }
    }

    fun get(id : Long) = productRepository.findByIdOrNull(id)

    fun getByBarcode(barcode : Long) = productRepository.findByBarcode(barcode).firstOrNull()

    data class ProductSearchCondition(
        val categoryIdIsNotNull : Boolean,
        val direction : String,
        val hasKeyword : Boolean = false
    )

    companion object{
        val NEXT_IN_SEARCH = ProductSearchCondition(false, "next", true)
        val PREV_IN_SEARCH = ProductSearchCondition(false, "prev", true)
        val NEXT_IN_CATEGORY = ProductSearchCondition(true, "next")
        val PREV_IN_CATEGORY = ProductSearchCondition(true, "prev")
        val DEFAULT_SEARCH = ProductSearchCondition(false, "next", false)
        val PREV_DEFAULT_SEARCH = ProductSearchCondition(false, "prev", false)
    }

}