package com.whammy.article

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class ArticleRepositoryTest {
    @Test
    fun testGetArticles() {
        val driver = mockk<ArticleDriver>()
        val repository = ArticleRepository(driver)
        val articles = Articles(listOf(
            Article("title1", "body", LocalDateTime.of(2020,1,1,0,0))
        ))

        val articleModels = listOf(
            ArticleModel("title1", "body", LocalDateTime.of(2020,1,1,0,0))
        )

        every { driver.getArticles() } returns articleModels

        assertEquals(articles, repository.getArticles())
    }
}