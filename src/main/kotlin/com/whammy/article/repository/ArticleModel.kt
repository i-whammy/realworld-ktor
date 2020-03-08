package com.whammy.article.repository

import java.time.LocalDateTime

data class ArticleModel(
    val slug: String,
    val title: String,
    val body: String,
    val updated: LocalDateTime,
    val comments: List<CommentModel>
)

data class CommentModel(val id: Int, val body: String, val createdAt: LocalDateTime, val updatedAt: LocalDateTime? = null)
