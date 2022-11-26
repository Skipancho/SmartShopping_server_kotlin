package com.example.smartshopping.domain.purchase

import com.example.smartshopping.common.SmartShoppingException
import com.example.smartshopping.domain.auth.UserContextHolder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PurchaseService @Autowired constructor(
    private val purchaseRepository: PurchaseRepository,
    private val userContextHolder: UserContextHolder
) {
    fun getPurchaseRecord(year: Int, month: Int): List<PurchaseRecord> {
        return userContextHolder.userCode?.let {
            purchaseRepository.findByUserCodeAndYearAndMonthOrderByIdDesc(it, year, month)
        } ?: throw SmartShoppingException("구매 기록 조회에 필요한 사용자 정보가 존재하지 않습니다.")
    }
}