package com.whammy.article.usecase

import com.whammy.article.domain.*
import com.whammy.article.exception.ArticleNotFoundException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
                    LocalDateTime.of(2019, 1, 1, 0, 0),
                    Comments(emptyList()),
                    emptyList()
                ),
                Article(
                    "title-2",
                    "title2",
                    "body2",
                    LocalDateTime.of(2020, 1, 1, 0, 0),
                    Comments(emptyList()),
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
                    Comments(emptyList()),
                    emptyList()
                ),
                Article(
                    "title-1",
                    "title1",
                    "body1",
                    LocalDateTime.of(2019, 1, 1, 0, 0),
                    Comments(emptyList()),
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
            Comments(emptyList()),
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
        val email = "taro@example.com"
        val slug = "slug-1"
        val body = "body"
        val newComment = mockk<Comment>()
        val comments = mockk<Comments>()
        val newComments = mockk<Comments>()

        every { repository.getCommentsOfArticle(slug) } returns comments
        every { comments.add(email, body) } returns newComments
        every { repository.saveComments(slug, newComments) } returns newComments
        every { newComments.getLatestComment() } returns newComment

        assertEquals(newComment, usecase.addComment(email, slug, body))

        verify {
            repository.getCommentsOfArticle(slug)
            comments.add(email, body)
            newComments.getLatestComment()
        }
    }

    @Test
    fun testFailedToAddCommentToArticle() {
        val repository = mockk<IArticleRepository>()
        val usecase = ArticleUsecase(repository)
        val email = "taro@example.com"
        val slug = "no-article-slug-1"
        val body = "body"

        every { repository.getCommentsOfArticle(slug) } throws ArticleNotFoundException("")

        assertThrows<ArticleNotFoundException> { usecase.addComment(email, slug, body) }

        verify {
            repository.getCommentsOfArticle(slug)
        }
    }

    @Test
    internal fun testToggleFavorite() {
        val repository = mockk<IArticleRepository>()
        val usecase = ArticleUsecase(repository)
        val slug = "slug"
        val user = "user@example.com"
        val article = mockk<Article>()
        val likedArticle = Article(slug, "title", "body", mockk(), Comments(emptyList()), listOf(Favorite(user)))
        val storedArticle = mockk<Article>()

        every { repository.getArticle(slug) } returns article
        every { article.toggleFavoriteFrom(user) } returns likedArticle
        every { repository.saveArticle(likedArticle) } returns storedArticle

        assertEquals(storedArticle, usecase.toggleFavorite(slug, user))

        verify {
            repository.getArticle(slug)
            article.toggleFavoriteFrom(user)
        }
    }

    @Test
    internal fun testFailedToGetArticleWhenTogglingFavorite() {
        val repository = mockk<IArticleRepository>()
        val usecase = ArticleUsecase(repository)

        every { repository.getArticle("no-article") } throws ArticleNotFoundException("")

        assertThrows<ArticleNotFoundException> { usecase.toggleFavorite("no-article", "no-one@example.com") }

        verify { repository.getArticle("no-article") }
    }
}