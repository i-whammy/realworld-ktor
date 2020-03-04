package com.whammy.article

import java.time.LocalDateTime

data class ArticleModel(val slug: String, val title: String, val body: String, val updated: LocalDateTime)
