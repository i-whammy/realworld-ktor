package com.whammy.article.repository

import com.whammy.article.domain.Article
import com.whammy.article.domain.Articles
import com.whammy.article.repository.ArticleDriver
import com.whammy.article.repository.ArticleModel
import com.whammy.article.repository.ArticleRepository
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
        val articles = Articles(
            listOf(
                Article(
                    "title-1",
                    "title1",
                    "body",
                    LocalDateTime.of(2020, 1, 1, 0, 0)
                )
            )
        )

        val articleModels = listOf(
            ArticleModel(
                "title-1",
                "title1",
                "body",
                LocalDateTime.of(2020, 1, 1, 0, 0)
            )
        )

        every { driver.getArticles() } returns articleModels

        assertEquals(articles, repository.getArticles())
    }

    @Test
    fun testGetArticle() {
        val driver = mockk<ArticleDriver>()
        val repository = ArticleRepository(driver)
        val article = Article(
            "title-1",
            "title1",
            "body",
            LocalDateTime.of(2020, 1, 1, 0, 0)
        )

        val articleModel = ArticleModel(
            "title-1",
            "title1",
            "body",
            LocalDateTime.of(2020, 1, 1, 0, 0)
        )

        every { driver.getArticle("title-1") } returns articleModel

        assertEquals(article, repository.getArticle("title-1"))
    }
}