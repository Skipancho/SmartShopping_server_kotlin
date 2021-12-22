package com.example.smartshopping.controller

import com.example.smartshopping.common.ApiResponse
import com.example.smartshopping.domain.auth.signup.SignupRequest
import com.example.smartshopping.domain.auth.signup.SignupService
import com.example.smartshopping.domain.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class UserApiController @Autowired constructor(
    private val signupService: SignupService,
    private val userService: UserService
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

}