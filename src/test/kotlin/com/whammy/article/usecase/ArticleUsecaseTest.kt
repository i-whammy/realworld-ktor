package com.whammy.article.usecase

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
import kotlin.test.Ignore
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
                    LocalDateTime.of(2019, 1, 1, 0, 0),
                    emptyList()
                ),
                Article(
                    "title-2",
                    "title2",
                    "body2",
                    LocalDateTime.of(2020, 1, 1, 0, 0),
                    emptyList()
                )
            )
        )
        val expected = Articles(
            listOf(
                Article(
                    "title-2",
                    "title2",
                    "body2",
                    LocalDateTime.of(2020, 1, 1, 0, 0),
                    emptyList()
                ),
                Article(
                    "title-1",
                    "title1",
                    "body1",
                    LocalDateTime.of(2019, 1, 1, 0, 0),
                    emptyList()
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
            LocalDateTime.of(2020, 1, 1, 0, 0),
            emptyList()
        )

        every { repository.getArticle("title-1") } returns article

        assertEquals(article, usecase.getArticle("title-1"))

        verify { repository.getArticle("title-1") }
    }

    @Test
    fun testFailedToGetArticle() {
        val repository = mockk<IArticleRepository>()
        val usecase = ArticleUsecase(repository)

        every { repository.getArticle("no-article") } throws ArticleNotFoundException("")

        assertThrows<ArticleNotFoundException> { usecase.getArticle("no-article") }

        verify { repository.getArticle("no-article") }
    }

    @Test
    fun testGetCommentsOfArticle() {
        val repository = mockk<IArticleRepository>()
        val usecase = ArticleUsecase(repository)
        val comments = mockk<Comments>()

        every { repository.getCommentsOfArticle("slug") } returns comments
        assertEquals(comments, usecase.getCommentsOfArticle("slug"))
        verify { repository.getCommentsOfArticle("slug") }
    }

    // TODO add comment to an existing article with authentication
    @Test
    fun testAddCommentToArticle() {
        val repository = mockk<IArticleRepository>()
        val usecase = ArticleUsecase(repository)
        val slug = "slug-1"
        val body = "body"
        val newComment = mockk<Comment>()
        val comments = mockk<Comments>()
        val newComments = mockk<Comments>()

        every { repository.getCommentsOfArticle(slug) } returns comments
        every { comments.add(body) } returns newComments
        every { repository.saveComments(slug,newComments) } returns newComments
        every { newComments.getLatestComment() } returns newComment

        assertEquals(newComment, usecase.addComment(slug, body))

        verify {
            repository.getCommentsOfArticle(slug)
            comments.add(body)
            newComments.getLatestComment()
        }
    }

    @Test
    fun testFailedToAddCommentToArticle() {
        val repository = mockk<IArticleRepository>()
        val usecase = ArticleUsecase(repository)
        val slug = "no-article-slug-1"
        val body = "body"

        every { repository.getCommentsOfArticle(slug) } throws ArticleNotFoundException("")

        assertThrows<ArticleNotFoundException> { usecase.addComment(slug, body) }

        verify {
            repository.getCommentsOfArticle(slug)
        }
    }
}