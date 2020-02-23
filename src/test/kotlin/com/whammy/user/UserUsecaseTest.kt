package com.whammy.user

import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class UserUsecaseTest {

    private val userRepository = mockk<IUserRepository>()

    private val usecase = UserUsecase(userRepository)

    @Test
    fun testLogin() {
        val email = "mail address"
        val password = "password"
        val loginInformation = LoginInformation(email, password)
        val user = mockk<User>()
        val refreshedUser = mockk<User>()

        every { userRepository.isUserExists(email, password) } returns true
        every { userRepository.findUserByEmailAddress(email) } returns user
        every { user.refreshToken() } returns refreshedUser
        every { userRepository.save(refreshedUser) } just Runs

        assertEquals(refreshedUser, usecase.login(loginInformation))

        verify {
            userRepository.isUserExists(email, password)
            userRepository.findUserByEmailAddress(email)
            user.refreshToken()
            userRepository.save(refreshedUser)
        }
    }

    @Test
    fun testLoginFailsWhenUserNotFound() {
        val email = "mail address"
        val password = "password"
        val loginInformation = LoginInformation(email, password)

        every { userRepository.isUserExists(email, password) } returns false

        assertThrows<UserNotFoundException> { usecase.login(loginInformation) }

        verify {
            userRepository.isUserExists(email, password)
        }
    }
}