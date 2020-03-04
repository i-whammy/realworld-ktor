package com.whammy.article.driver

import com.whammy.article.repository.ArticleDriver
import com.whammy.article.repository.ArticleModel
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class InMemoryArticleDriver: ArticleDriver {
    private val articles = listOf(
        ArticleModel(
            "Title-1",
            "Title 1",
            "This is a sample article.",
            LocalDateTime.of(2020, 1, 1, 12, 0)
        ),
        ArticleModel(
            "Title-2",
            "Title 2",
            "This article would be latest.",
            LocalDateTime.of(2020, 1, 1, 18, 0)
        )
    )

    override fun getArticles(): List<ArticleModel> {
        return articles
    }

    override fun getArticle(slug: String): ArticleModel {
        return articles.find { it.slug == slug }!!
    }
}