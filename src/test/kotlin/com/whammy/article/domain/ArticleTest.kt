package com.whammy.article.domain

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class ArticleTest {
    @Test
    internal fun testToggleLikeFrom() {
        val updatedAt = mockk<LocalDateTime>()
        val article = Article("slug", "title", "body", "user@example.com", updatedAt, Comments(emptyList()), emptyList())
        val expected = Article(
            "slug",
            "title",
            "body",
            "user@example.com",
            updatedAt,
            Comments(emptyList()),
            listOf(Favorite("user@example.com"))
        )
        assertEquals(expected, article.toggleFavoriteFrom("user@example.com"))
    }

    @Test
    internal fun testToggleLikeFromWhenAlreadyLikedByTheUser() {
        val updatedAt = mockk<LocalDateTime>()
        val article = Article(
            "slug",
            "title",
            "body",
            "user@example.com",
            updatedAt,
            Comments(emptyList()),
            listOf(Favorite("user@example.com"))
        )
        val expected = Article("slug", "title", "body", "user@example.com", updatedAt, Comments(emptyList()), emptyList())
        assertEquals(expected, article.toggleFavoriteFrom("user@example.com"))
    }

    @Test
    internal fun testArticleOf() {
        mockkStatic(LocalDateTime::class)
        val createdDateTime = LocalDateTime.of(2020, 1, 1, 1, 0)

        every { LocalDateTime.now() } returns createdDateTime
        val expected = Article("title-1", "title 1", "body", "user@example.com", createdDateTime, Comments(emptyList()), emptyList())

        assertEquals(expected, Article.of("user@example.com", "title 1", "body"))
    }
}