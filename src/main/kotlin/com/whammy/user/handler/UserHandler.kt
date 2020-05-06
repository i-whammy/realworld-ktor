package com.whammy.user.handler

import com.whammy.user.domain.LoginInformation
import com.whammy.user.usecase.UserUsecase
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post

fun Route.userHandler(userUsecase: UserUsecase) {
    post("api/users/login") {
        val request = call.receive<LoginRequest>()
        val user = userUsecase.login(LoginInformation(request.user.email, request.user.password))
        call.respond(LoginResponse(LoginResponse.UserResponse(user.email, user.token)))
    }
}

data class LoginRequest(val user: LoginRequest.UserRequest) {
    data class UserRequest(val email: String, val password: String)
}

data class LoginResponse(val user: LoginResponse.UserResponse) {
    data class UserResponse(val email: String, val token: String)
}