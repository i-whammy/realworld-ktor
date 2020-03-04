package com.whammy.article

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.time.LocalDateTime

@Controller
@RequestMapping("/api/articles")
public class ArticleController(private val articleUsecase: ArticleUsecase) {

    @RequestMapping("/", method = [RequestMethod.GET])
    fun getArticles() : ResponseEntity<ArticlesResponse> {
        return ResponseEntity.ok(
            ArticlesResponse(articleUsecase.getArticlesOrderedByUpdatedDateTime().map {
                ArticleResponse(it.slug, it.title, it.body, it.updatedAt)
            }))
    }

    @RequestMapping("/{slug}", method = [RequestMethod.GET])
    fun getArticle(@PathVariable("slug") slug: String) : ResponseEntity<ArticleResponse> {
        return ResponseEntity.ok(
            articleUsecase.getArticle(slug).let { ArticleResponse(it.slug, it.title, it.body, it.updatedAt) }
        )
    }
}

data class ArticlesResponse(
    @JsonProperty("articles") val articles: List<ArticleResponse>)

data class ArticleResponse(
    @JsonProperty("slug") val slug: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("body") val body: String,
    @JsonProperty("updatedAt") val updatedAt: LocalDateTime)