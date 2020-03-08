package com.whammy.article.domain

import com.whammy.article.exception.CommentNotFoundException
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import kotlin.test.assertEquals

class CommentsTest {

    @Test
    internal fun testAddCommentWithIncrementalIdAndAddedTime() {
        val createdAt = mockk<LocalDateTime>()
        mockkStatic(LocalDateTime::class)
        val comments = Comments(listOf(
            Comment(1,"body", LocalDateTime.of(2020,1,1,12,0)),
            Comment(2,"body", LocalDateTime.of(2020,1,1,12,0))
        ))
        val newComments = Comments(listOf(
            Comment(1,"body", LocalDateTime.of(2020,1,1,12,0)),
            Comment(2,"body", LocalDateTime.of(2020,1,1,12,0)),
            Comment(3,"New comment body", createdAt)
        ))
        every { LocalDateTime.now() } returns createdAt

        assertEquals(newComments, comments.add("New comment body"))

        verify { LocalDateTime.now() }
    }

    @Test
    internal fun testAddCommentAsFirstComment() {
        val createdAt = mockk<LocalDateTime>()
        mockkStatic(LocalDateTime::class)
        val comments = Comments(emptyList())
        val newComments = Comments(listOf(Comment(1,"New comment body", createdAt)))
        every { LocalDateTime.now() } returns createdAt

        assertEquals(newComments, comments.add("New comment body"))

        verify { LocalDateTime.now() }
    }

    @Test
    internal fun testGetLatestComment() {
        val latestComment = Comment(3, "body", mockk())
        val comments = Comments(
            listOf(
                Comment(1, "body", mockk()),
                Comment(2, "body", mockk()),
                latestComment
            )
        )

        assertEquals(latestComment, comments.getLatestComment())
    }

    @Test
    internal fun testFailedToGetLatestCommentWhenTheArticleHasNoComment() {
        assertThrows<CommentNotFoundException> { Comments(emptyList()).getLatestComment() }
    }
}