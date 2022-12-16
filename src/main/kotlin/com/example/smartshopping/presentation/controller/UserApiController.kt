package com.example.smartshopping.presentation.controller

import com.example.smartshopping.common.ApiResponse
import com.example.smartshopping.domain.auth.signin.SigninRequest
import com.example.smartshopping.domain.auth.signin.SigninService
import com.example.smartshopping.domain.auth.signup.SignupRequest
import com.example.smartshopping.domain.auth.signup.SignupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class UserApiController @Autowired constructor(
    private val signupService: SignupService,
    private val signinService: SigninService
){
    @PostMapping("/users")
    fun signup(@RequestBody signupRequest: SignupRequest)=
        ApiResponse.ok(signupService.signup(signupRequest))

    @PostMapping("/users/id")
    fun validateUserId(@RequestParam userId : String) =
        ApiResponse.ok(signupService.checkDuplicatesId(userId))

    @PostMapping("/users/nickname")
    fun validateNickName(@RequestParam nickName : String) =
        ApiResponse.ok(signupService.checkDuplicatesNickName(nickName))

    @PostMapping("/signin")
    fun signin(@RequestBody signinRequest: SigninRequest)=
        ApiResponse.ok(signinService.signin(signinRequest))
}