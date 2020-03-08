package com.whammy.article.domain

import java.time.LocalDateTime

data class Comment(val id: Int, val body: String, val createdAt: LocalDateTime, val updatedAt: LocalDateTime)