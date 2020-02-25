package com.whammy.user

import org.springframework.stereotype.Service

@Service
class UserUsecase(private val userRepository: IUserRepository) {
    fun login(loginInformation: LoginInformation): User {
        val email = loginInformation.email
        val password = loginInformation.password
        if (userRepository.isUserExists(email, password)) {
            val user = userRepository.findUserByEmailAddress(email).refreshToken()
            userRepository.save(user)
            return user
        } else throw LoginFailureException("The user with $email does not exist.")
    }
}
