package com.whammy.user

interface UserDriver {
    fun findUserByEmailAddress(email: String) : UserModel?
    fun findUserByEmailAddressAndPassword(email: String, password: String): UserModel?
    fun save(user: UserModel)
}