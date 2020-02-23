package com.whammy.user

// TODO This controller is very very first version. I need to do a lot of things in order to accept HTTP Request to this controller...

public class UserController {
    private val usecase = UserUsecase(UserRepository(InMemoryUserDriver()))

    fun login(email: String, password: String) {
        usecase.login(LoginInformation(email, password))
    }
}