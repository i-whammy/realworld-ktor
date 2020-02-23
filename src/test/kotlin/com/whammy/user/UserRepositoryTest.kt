package com.whammy.user

import io.mockk.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UserRepositoryTest {

    private val driver = mockk<UserDriver>()

    private val repository = UserRepository(driver)

    @Test
    fun testIsUserExistsReturnTrueWhenUserModelIsReturned() {
        val email = "aaa@example.com"
        val password = "password"
        every { driver.findUserByEmailAddressAndPassword(email, password) } returns UserModel(email, password, "token1")

        assertTrue { repository.isUserExists(email, password) }

        verify { driver.findUserByEmailAddressAndPassword(email, password) }
    }

    @Test
    fun testIsUserExistsReturnFalseWhenNullIsReturned() {
        val email = "aaa@example.com"
        val password = "password"
        every { driver.findUserByEmailAddressAndPassword(email, password) } returns null

        assertFalse { repository.isUserExists(email, password) }

        verify { driver.findUserByEmailAddressAndPassword(email, password) }
    }

    @Test
    fun testFindUserByEmailAddress() {
        val email = "aaa@example.com"
        val password = "password"
        val token = "token1"
        val userModel = UserModel(email, password, token)
        val user = User(email, password, token)

        every { driver.findUserByEmailAddress(email) } returns userModel

        assertEquals(user, repository.findUserByEmailAddress(email))

        verify { driver.findUserByEmailAddress(email) }
    }

    @Test
    fun testSave() {
        val email = "aaa@example.com"
        val password = "password"
        val token = "token1"
        val userModel = UserModel(email, password, token)
        val user = User(email, password, token)

        every { driver.save(userModel) } just Runs

        repository.save(user)

        verify { driver.save(userModel) }
    }
}