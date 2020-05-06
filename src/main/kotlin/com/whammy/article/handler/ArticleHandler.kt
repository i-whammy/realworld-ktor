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
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import java.time.LocalDateTime

fun Route.articleHandler(articleUsecase: ArticleUsecase, userService: UserService) {
    get("/api/articles") {
        call.respond(
            articleUsecase.getArticlesOrderedByUpdatedDateTime().map { it.convertToArticleResponse() }
                .let { ArticlesResponse(it, it.size) })
    }

    post("/api/articles") {
        val request = call.receive<ArticleRequest>()
        val authorizationHeader = call.request.headers["Authorization"]!!
        val userId = userService.getUserId(authorizationHeader)
        call.respond(
            articleUsecase.createNewArticle(userId, request.title, request.body).convertToArticleResponse()
        )
    }

    put("/api/articles/{slug}") {
        val request = call.receive<ArticleRequest>()
        val slug = call.parameters["slug"]!!
        val authorizationHeader = call.request.headers["Authorization"]!!
        val userId = userService.getUserId(authorizationHeader)
        call.respond(
            articleUsecase.updateArticle(slug, userId, request.title, request.body).convertToArticleResponse()
        )
    }

//    @RequestMapping("/{slug}", method = [RequestMethod.PUT])
//    fun updateArticle(@RequestHeader("Authorization", required = true) authorizationHeader: String, @PathVariable slug: String, @RequestBody article: UpdateArticleRequest): ResponseEntity<ArticleResponse> {
//        val userId = userService.getUserId(authorizationHeader)
//        return ResponseEntity.ok(articleUsecase.updateArticle(slug, userId, article.title, article.body).convertToArticleResponse())
//    }
//
//    @RequestMapping("/{slug}", method = [RequestMethod.GET])
//    fun getSingleArticle(@PathVariable("slug") slug: String): ResponseEntity<ArticleResponse> {
//        return ResponseEntity.ok(articleUsecase.getArticle(slug).convertToArticleResponse())
//    }
//
//    @RequestMapping("/{slug}/comments", method = [RequestMethod.GET])
//    fun getComments(@PathVariable("slug") slug: String): ResponseEntity<CommentsResponse> {
//        return ResponseEntity.ok(
//            articleUsecase.getCommentsOfArticle(slug).map { it.convertToCommentResponse() }.let { CommentsResponse(it) })
//    }
//
//    @RequestMapping("/{slug}/comments", method = [RequestMethod.POST])
//    fun postComment(@RequestHeader("Authorization", required = true) authorizationHeader: String, @PathVariable("slug") slug:String, @RequestBody comment: CommentRequest) : ResponseEntity<CommentResponse> {
//        val userId = userService.getUserId(authorizationHeader)
//        return ResponseEntity.ok(articleUsecase.addComment(userId, slug, comment.body).convertToCommentResponse())
//    }
//
//    @RequestMapping("/{slug}/favorite", method = [RequestMethod.POST])
//    fun addFavorite(@RequestHeader("Authorization", required = true) authorizationHeader: String, @PathVariable("slug") slug: String) : ResponseEntity<ArticleResponse> {
//        val userId = userService.getUserId(authorizationHeader)
//        return ResponseEntity.ok(articleUsecase.toggleFavorite(userId, slug).convertToArticleResponse())
//    }
//
//    @RequestMapping("/{slug}/favorite", method = [RequestMethod.DELETE])
//    fun unfavorite(@RequestHeader("Authorization", required = true) authorizationHeader: String, @PathVariable("slug") slug: String) : ResponseEntity<ArticleResponse> {
//        val userId = userService.getUserId(authorizationHeader)
//        return ResponseEntity.ok(articleUsecase.toggleFavorite(userId, slug).convertToArticleResponse())
//    }
//
//    @RequestMapping("/{slug}", method = [RequestMethod.DELETE])
//    fun deleteArticle(@PathVariable("slug") slug: String) {
//        articleUsecase.delete(slug)
//    }
}


private fun Article.convertToArticleResponse(): ArticleResponse {
    return ArticleResponse(slug, title, body, createdAt, favorites.isNotEmpty(), favorites.count())
}

private fun Comment.convertToCommentResponse(): CommentResponse {
    return CommentResponse(id, body, createdAt, updatedAt)
}

@JsonRootName("article")
data class ArticleRequest(
    @JsonProperty("title", required = true) val title: String,
    @JsonProperty("body", required = true) val body: String
)

@JsonRootName("article")
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
    val updatedAt: LocalDateTime,
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