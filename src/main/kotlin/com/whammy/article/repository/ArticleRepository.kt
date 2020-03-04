package com.whammy.article.repository

import com.whammy.article.domain.Article
import com.whammy.article.domain.Articles
import com.whammy.article.exception.ArticleNotFoundException
import com.whammy.article.usecase.IArticleRepository
import org.springframework.stereotype.Repository

@Repository
class ArticleRepository(private val driver: ArticleDriver):
    IArticleRepository {
    override fun getArticles(): Articles {
        return Articles(driver.getArticles().map {
            Article(
                it.slug,
                it.title,
                it.body,
                it.updated
            )
        })
    }

    override fun getArticle(slug: String): Article {
        return driver.getArticle(slug)?.let { Article(it.slug, it.title, it.body, it.updated) }
            ?: throw ArticleNotFoundException("Article not found. slug = $slug")
    }
}