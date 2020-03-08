package com.whammy.article.repository

import com.whammy.article.domain.Article
import com.whammy.article.domain.Articles
import com.whammy.article.domain.Comment
import com.whammy.article.domain.Comments
import com.whammy.article.exception.ArticleNotFoundException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
                    LocalDateTime.of(2020, 1, 1, 0, 0),
                    emptyList()
                )
            )
        )

        val articleModels = listOf(
            ArticleModel(
                "title-1",
                "title1",
                "body",
                LocalDateTime.of(2020, 1, 1, 0, 0),
                emptyList()
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
            LocalDateTime.of(2020, 1, 1, 0, 0),
            emptyList()
        )

        val articleModel = ArticleModel(
            "title-1",
            "title1",
            "body",
            LocalDateTime.of(2020, 1, 1, 0, 0),
            emptyList()
        )

        every { driver.getArticle("title-1") } returns articleModel

        assertEquals(article, repository.getArticle("title-1"))
    }

    @Test
    fun testFailedToGetArticle() {
        val driver = mockk<ArticleDriver>()
        val repository = ArticleRepository(driver)

        every { driver.getArticle("no-article") } returns null

        assertThrows<ArticleNotFoundException> { repository.getArticle("no-article") }
    }

    @Test
    fun testGetCommentsOfArticle() {
        val driver = mockk<ArticleDriver>()
        val repository = ArticleRepository(driver)
        val models = listOf(CommentModel(1, "body1", LocalDateTime.of(2020,1,1,10,0)))
        val comments = Comments(listOf(Comment(1, "body1", LocalDateTime.of(2020,1,1,10,0))))

        every { driver.getArticle("slug1") } returns mockk()
        every { driver.getCommentsOfArticle("slug1") } returns models

        assertEquals(comments, repository.getCommentsOfArticle("slug1"))

        verify {
            driver.getArticle("slug1")
            driver.getCommentsOfArticle("slug1") }
    }

    @Test
    fun testGetEmptyCommentsOfArticle() {
        val driver = mockk<ArticleDriver>()
        val repository = ArticleRepository(driver)
        val models = emptyList<CommentModel>()

        every { driver.getArticle("slug1") } returns mockk()
        every { driver.getCommentsOfArticle("slug1") } returns models

        assertEquals(Comments(emptyList()), repository.getCommentsOfArticle("slug1"))

        verify {
            driver.getArticle("slug1")
            driver.getCommentsOfArticle("slug1") }
    }

    @Test
    fun testGetCommentsFailedWhenNoArticleExists() {
        val driver = mockk<ArticleDriver>()
        val repository = ArticleRepository(driver)
        val models = emptyList<CommentModel>()

        every { driver.getArticle("no-article") } returns null

        assertThrows<ArticleNotFoundException> { repository.getCommentsOfArticle("no-article") }

        verify { driver.getArticle("no-article") }
    }

    @Test
    internal fun testSaveComments() {
        val driver = mockk<ArticleDriver>()
        val repository = ArticleRepository(driver)
        val slug = "slug"
        val models = listOf(CommentModel(1, "body1", LocalDateTime.of(2020,1,1,10,0)))
        val comments = Comments(listOf(Comment(1, "body1", LocalDateTime.of(2020,1,1,10,0))))

        every { driver.saveComments(slug, models) } returns models

        assertEquals(comments, repository.saveComments(slug, comments))

        verify { driver.saveComments(slug, models) }
    }
}