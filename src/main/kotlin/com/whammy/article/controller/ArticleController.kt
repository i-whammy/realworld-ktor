package com.whammy.article.controller

import com.fasterxml.jackson.annotation.JsonProperty
import com.whammy.article.usecase.ArticleUsecase
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.time.LocalDateTime

@Controller
@RequestMapping("/api/articles")
class ArticleController(private val articleUsecase: ArticleUsecase) {

    @RequestMapping("/", method = [RequestMethod.GET])
    fun getArticles(): ResponseEntity<ArticlesResponse> {
        return ResponseEntity.ok(
            articleUsecase.getArticlesOrderedByUpdatedDateTime().map {
                ArticleResponse(
                    it.slug,
                    it.title,
                    it.body,
                    it.updatedAt
                )
            }.let { ArticlesResponse(it) })
    }

    @RequestMapping("/{slug}", method = [RequestMethod.GET])
    fun getSingleArticle(@PathVariable("slug") slug: String): ResponseEntity<ArticleResponse> {
        return ResponseEntity.ok(
            articleUsecase.getArticle(slug).let {
                ArticleResponse(
                    it.slug,
                    it.title,
                    it.body,
                    it.updatedAt
                )
            }
        )
    }

    @RequestMapping("/{slug}/comments", method = [RequestMethod.GET])
    fun getComments(@PathVariable("slug") slug: String): ResponseEntity<CommentsResponse> {
        return ResponseEntity.ok(
            articleUsecase.getCommentsOfArticle(slug).map {
                CommentResponse(
                    it.id,
                    it.body,
                    it.createdAt,
                    it.updatedAt
                )
            }.let { CommentsResponse(it) })
    }
}

data class ArticlesResponse(
    @JsonProperty("articles") val articles: List<ArticleResponse>
)

data class ArticleResponse(
    @JsonProperty("slug") val slug: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("body") val body: String,
    @JsonProperty("updatedAt") val updatedAt: LocalDateTime
)

data class CommentsResponse(
    @JsonProperty("comments") val comments: List<CommentResponse>
)

data class CommentResponse(
    @JsonProperty("id") val id: Int,
    @JsonProperty("body") val body: String,
    @JsonProperty("createdAt") val createdAt: LocalDateTime,
    @JsonProperty("updatedAt") val updatedAt: LocalDateTime?
)