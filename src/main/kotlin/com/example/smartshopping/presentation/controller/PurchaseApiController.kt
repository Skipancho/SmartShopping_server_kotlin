package com.example.smartshopping.presentation.controller

import com.example.smartshopping.common.ApiResponse
import com.example.smartshopping.domain.request.PurchaseRequest
import com.example.smartshopping.domain.response.toPurchaseResponse
import com.example.smartshopping.domain.service.PurchaseService
import com.example.smartshopping.entity.PurchaseRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class PurchaseApiController @Autowired constructor(
    private val purchaseService: PurchaseService
) {
    @GetMapping("/purchase")
    fun getPurchaseRecord(
        @RequestParam year: Int,
        @RequestParam month: Int
    ) = purchaseService.getPurchaseRecord(year, month)
        .map(PurchaseRecord::toPurchaseResponse)
        .let { ApiResponse.ok(it) }

    @PostMapping("/purchase")
    fun register(
        @RequestBody request: List<PurchaseRequest>
    ) = ApiResponse.ok(
        purchaseService.register(request)
    )
}