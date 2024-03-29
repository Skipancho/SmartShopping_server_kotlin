package com.example.smartshopping.presentation.controller

import com.example.smartshopping.common.ApiResponse
import com.example.smartshopping.domain.request.SigninRequest
import com.example.smartshopping.domain.service.SigninService
import com.example.smartshopping.domain.request.SignupRequest
import com.example.smartshopping.domain.service.SignupService
import com.example.smartshopping.domain.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class UserApiController @Autowired constructor(
    private val signupService: SignupService,
    private val signinService: SigninService,
    private val userService: UserService
) {
    @PostMapping("/users")
    fun signup(@RequestBody signupRequest: SignupRequest) =
        ApiResponse.ok(signupService.signup(signupRequest))

    @PutMapping("/users")
    fun updateUserInfo(
        @RequestBody signupRequest: SignupRequest
    ) = ApiResponse.ok(userService.updateUserInfo(signupRequest))

    @DeleteMapping("/users")
    fun withdrawal() = ApiResponse.ok(userService.deleteUserInfo())

    @PostMapping("/users/id")
    fun validateUserId(@RequestParam userId: String) =
        ApiResponse.ok(signupService.checkDuplicatesId(userId))

    @PostMapping("/users/nickname")
    fun validateNickName(@RequestParam nickName: String) =
        ApiResponse.ok(signupService.checkDuplicatesNickName(nickName))

    @PostMapping("/signin")
    fun signin(@RequestBody signinRequest: SigninRequest) =
        ApiResponse.ok(signinService.signin(signinRequest))
}