package com.example.smartshopping.controller

import com.example.smartshopping.common.ApiResponse
import com.example.smartshopping.domain.purchase.PurchaseRecord
import com.example.smartshopping.domain.purchase.PurchaseService
import com.example.smartshopping.domain.purchase.toPurchaseResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class PurchaseApiController @Autowired constructor(
    private val purchaseService: PurchaseService
){
    @GetMapping("/purchase")
    fun getPurchaseRecord(
        @RequestParam year : Int,
        @RequestParam month : Int
    ) = purchaseService.getPurchaseRecord(year, month)
        .map(PurchaseRecord :: toPurchaseResponse)
        .let { ApiResponse.ok(it) }
}