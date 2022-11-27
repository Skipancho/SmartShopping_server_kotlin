package com.example.smartshopping.domain.purchase

import com.example.smartshopping.common.SmartShoppingException
import com.example.smartshopping.domain.auth.UserContextHolder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

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

    fun register(purchaseRequest: List<PurchaseRequest>) {
        userContextHolder.userCode?.let { userCode ->
            val calendar = Calendar.getInstance()
            purchaseRequest.forEach {
                it.toPurchaseRecord(userCode, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1)
                    .run(::save)
            }
        } ?: throw SmartShoppingException("구매 기록 등록에 필요한 사용자 정보가 존재하지 않습니다.")
    }

    private fun save(purchaseRecord: PurchaseRecord) = purchaseRepository.save(purchaseRecord)
}