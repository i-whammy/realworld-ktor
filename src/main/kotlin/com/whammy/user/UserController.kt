package com.whammy.user

import org.springframework.stereotype.Controller

// TODO This controller is very very first version. I need to do a lot of things in order to accept HTTP Request to this controller...

@Controller
public class UserController(private val usecase: UserUsecase) {

    fun login(email: String, password: String) {
        usecase.login(LoginInformation(email, password))
    }
}