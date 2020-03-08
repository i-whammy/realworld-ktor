package com.whammy.user.domain

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class UserTest {

    @Test
    fun testRefreshTokenReturnsNewUserWithTheSameAddress() {
        val user = User("user@example.com", "password", "token1")
        val expected = User("user@example.com", "password", "token2")
        assertEquals(expected.email, user.refreshToken().email)
    }

    @Test
    fun testRefreshTokenReturnsNewUserWithTheSamePassword() {
        val user = User("user@example.com", "password", "token1")
        val expected = User("user@example.com", "password", "token2")
        assertEquals(expected.password, user.refreshToken().password)
    }

    @Test
    fun testRefreshTokenReturnsNewUserWithTheDifferentToken() {
        val user = User("user@example.com", "password", "token1")
        val expected = User("user@example.com", "password", "token2")
        assertNotEquals(expected.token, user.refreshToken().token)
    }
}