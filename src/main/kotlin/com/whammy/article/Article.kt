package com.whammy.article

import java.time.LocalDateTime

data class Article(val slug: String, val title: String, val body: String, val updatedAt: LocalDateTime)
