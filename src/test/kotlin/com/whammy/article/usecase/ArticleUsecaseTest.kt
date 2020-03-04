package com.whammy.article.usecase

import com.whammy.article.domain.Article
import com.whammy.article.domain.Articles
import com.whammy.article.usecase.ArticleUsecase
import com.whammy.article.usecase.IArticleRepository
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
        val articles = Articles(
            listOf(
                Article(
                    "title-1",
                    "title1",
                    "body1",
                    LocalDateTime.of(2019, 1, 1, 0, 0)
                ),
                Article(
                    "title-2",
                    "title2",
                    "body2",
                    LocalDateTime.of(2020, 1, 1, 0, 0)
                )
            )
        )
        val expected = Articles(
            listOf(
                Article(
                    "title-2",
                    "title2",
                    "body2",
                    LocalDateTime.of(2020, 1, 1, 0, 0)
                ),
                Article(
                    "title-1",
                    "title1",
                    "body1",
                    LocalDateTime.of(2019, 1, 1, 0, 0)
                )
            )
        )

        every { repository.getArticles() } returns articles

        assertEquals(expected, usecase.getArticlesOrderedByUpdatedDateTime())

        verify {
            repository.getArticles()
        }
    }

    @Test
    fun testGetArticle() {
        val repository = mockk<IArticleRepository>()
        val usecase = ArticleUsecase(repository)
        val article = Article(
            "title-1",
            "title1",
            "body",
            LocalDateTime.of(2020, 1, 1, 0, 0)
        )

        every { repository.getArticle("title-1") } returns article

        assertEquals(article, usecase.getArticle("title-1"))

        verify { repository.getArticle("title-1") }
    }

    @Test
    fun testFailedToGetArticle() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}