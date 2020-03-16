package com.whammy.article.domain

import io.mockk.mockk
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class ArticleTest {
    @Test
    internal fun testToggleLikeFrom() {
        val updatedAt = mockk<LocalDateTime>()
        val article = Article("slug", "title", "body", updatedAt, Comments(emptyList()), emptyList())
        val expected = Article("slug", "title", "body", updatedAt, Comments(emptyList()), listOf(Favorite("user@example.com")))
        assertEquals(expected, article.toggleFavoriteFrom("user@example.com"))
    }

    @Test
    internal fun testToggleLikeFromWhenAlreadyLikedByTheUser() {
        val updatedAt = mockk<LocalDateTime>()
        val article = Article("slug", "title", "body", updatedAt, Comments(emptyList()), listOf(Favorite("user@example.com")))
        val expected = Article("slug", "title", "body", updatedAt, Comments(emptyList()), emptyList())
        assertEquals(expected, article.toggleFavoriteFrom("user@example.com"))
    }
}