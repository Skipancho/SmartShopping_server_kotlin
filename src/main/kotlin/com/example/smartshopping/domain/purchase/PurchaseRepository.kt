package com.example.smartshopping.domain.purchase

import org.springframework.data.jpa.repository.JpaRepository

interface PurchaseRepository : JpaRepository<PurchaseRecord, Long> {
    fun findByUserCodeAndYearAndMonthOrderByIdDesc(
        userCode: Long, year: Int, month: Int
    ): List<PurchaseRecord>
}