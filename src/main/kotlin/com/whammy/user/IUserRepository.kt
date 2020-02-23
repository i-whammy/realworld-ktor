package com.whammy.user

interface IUserRepository {
    fun isUserExists(email: String, password: String): Boolean
    fun findUserByEmailAddress(email: String): User
    fun save(user: User)
}
