package com.whammy.user.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.whammy.user.domain.User
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Service
class UserService {
    fun issueNewToken(user: User): User {
        val hmaC256 = Algorithm.HMAC256("VGhpcyBrZXkgbXVzdCBub3QgYmUgZGVjb2RlZA==")
        return User(user.email, user.password, JWT.create()
            .withIssuer("Real World App")
            .withClaim("userId", user.email)
            .withExpiresAt(Date.from(LocalDateTime.now().plusHours(1L).atOffset(ZoneOffset.UTC).toInstant()))
            .sign(hmaC256))
    }
}