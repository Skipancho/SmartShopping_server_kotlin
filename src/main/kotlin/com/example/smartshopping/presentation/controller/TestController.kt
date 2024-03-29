package com.example.smartshopping.presentation.controller

import com.example.smartshopping.common.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping("/api/v1/hello")
    fun hello() = ApiResponse.ok("world")
}