package com.whammy.article

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class ArticleUsecaseTest {

    @Test
    fun testGetArticles() {
        val repository = mockk<IArticleRepository>()
        val usecase = ArticleUsecase(repository)
        val articles = Articles(listOf(
            Article("title-1", "title1", "body1", LocalDateTime.of(2019,1,1,0,0)),
            Article("title-2", "title2", "body2", LocalDateTime.of(2020,1,1,0,0))
        ))
        val expected = Articles(listOf(
            Article("title-2", "title2", "body2", LocalDateTime.of(2020,1,1,0,0)),
            Article("title-1", "title1", "body1", LocalDateTime.of(2019,1,1,0,0))
        ))

        every { repository.getArticles() } returns articles

        assertEquals(expected, usecase.getArticlesOrderedByUpdatedDateTime())

        verify {
            repository.getArticles()
        }
    }
}