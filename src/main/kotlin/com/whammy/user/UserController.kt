package com.whammy.user

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
@RequestMapping("/api/users")
public class UserController(private val usecase: UserUsecase) {

    @RequestMapping("/login", method = [RequestMethod.POST])
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val user = usecase.login(LoginInformation(request.user.email, request.user.password))
        return ResponseEntity.ok(LoginResponse(UserInformationResponse(user.email, user.token)))
    }
}

data class LoginRequest(
    @JsonProperty("user") var user: UserInformationRequest)

data class UserInformationRequest(
    @JsonProperty("email") var email: String,
    @JsonProperty("password") var password: String)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class LoginResponse(
    @JsonProperty("user") var user: UserInformationResponse)

@JsonPropertyOrder("email", "token")
data class UserInformationResponse(
    @JsonProperty("email") var email: String,
    @JsonProperty("token") var token: String)
