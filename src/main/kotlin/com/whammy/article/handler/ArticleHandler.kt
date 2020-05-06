package com.whammy.article.handler

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import com.whammy.article.domain.Article
import com.whammy.article.domain.Comment
import com.whammy.article.usecase.ArticleUsecase
import com.whammy.user.service.UserService
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import java.time.LocalDateTime

fun Route.articleHandler(articleUsecase: ArticleUsecase, userService: UserService) {
    route("/api/articles") {
        get("/") {
            call.respond(
                articleUsecase.getArticlesOrderedByUpdatedDateTime().map { it.convertToArticleResponse() }
                    .let { ArticlesResponse(it, it.size) })
        }

        post("/") {
            val request = call.receive<ArticleRequest>()
            val authorizationHeader = call.request.headers["Authorization"]!!
            val userId = userService.getUserId(authorizationHeader)
            call.respond(
                articleUsecase.createNewArticle(userId, request.title, request.body).convertToArticleResponse()
            )
        }

        route("/{slug}") {
            put("/") {
                val request = call.receive<UpdateArticleRequest>()
                val slug = call.parameters["slug"]!!
                val authorizationHeader = call.request.headers["Authorization"]!!
                val userId = userService.getUserId(authorizationHeader)
                call.respond(
                    articleUsecase.updateArticle(slug, userId, request.title, request.body).convertToArticleResponse()
                )
            }

            get("/") {
                val slug = call.parameters["slug"]!!
                call.respond(articleUsecase.getArticle(slug).convertToArticleResponse())
            }

            route("/comments") {
                get("/") {
                    val slug = call.parameters["slug"]!!
                    call.respond(articleUsecase.getCommentsOfArticle(slug).map { it.convertToCommentResponse() }.let { CommentsResponse(it) })
                }

                post("/") {
                    val request = call.receive<CommentRequest>()
                    val slug = call.parameters["slug"]!!
                    val authorizationHeader = call.request.headers["Authorization"]!!
                    val userId = userService.getUserId(authorizationHeader)
                    call.respond(articleUsecase.addComment(userId, slug, request.body).convertToCommentResponse())
                }
            }

            route("/favorite") {
                post("/") {
                    val slug = call.parameters["slug"]!!
                    val authorizationHeader = call.request.headers["Authorization"]!!
                    val userId = userService.getUserId(authorizationHeader)
                    call.respond(articleUsecase.toggleFavorite(slug, userId).convertToArticleResponse())
                }

                delete("/") {
                    val slug = call.parameters["slug"]!!
                    val authorizationHeader = call.request.headers["Authorization"]!!
                    val userId = userService.getUserId(authorizationHeader)
                    call.respond(articleUsecase.toggleFavorite(slug, userId).convertToArticleResponse())
                }
            }
        }
    }

//
//    @RequestMapping("/{slug}", method = [RequestMethod.DELETE])
//    fun deleteArticle(@PathVariable("slug") slug: String) {
//        articleUsecase.delete(slug)
//    }
}


private fun Article.convertToArticleResponse(): ArticleResponse {
    return ArticleResponse(slug, title, body, createdAt, updatedAt, favorites.isNotEmpty(), favorites.count())
}

private fun Comment.convertToCommentResponse(): CommentResponse {
    return CommentResponse(id, body, createdAt, updatedAt)
}

data class ArticleRequest(
    val title: String,
    val body: String
)

data class UpdateArticleRequest(
    @JsonProperty("title") val title: String,
    @JsonProperty("body") val body: String
)

data class ArticlesResponse(
    val articles: List<ArticleResponse>,
    val articlesCount: Int
)

data class ArticleResponse(
    val slug: String,
    val title: String,
    val body: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val favorited: Boolean,
    val favoritesCount: Int
)

@JsonRootName("comment")
data class CommentRequest(
    @JsonProperty("body", required = true) val body: String
)

data class CommentsResponse(
    @JsonProperty("comments") val comments: List<CommentResponse>
)

@JsonRootName("comment")
data class CommentResponse(
    @JsonProperty("id") val id: Int,
    @JsonProperty("body") val body: String,
    @JsonProperty("createdAt") val createdAt: LocalDateTime,
    @JsonProperty("updatedAt") val updatedAt: LocalDateTime?
)