package com.whammy.article

import org.springframework.stereotype.Repository

@Repository
class ArticleRepository(private val driver: ArticleDriver): IArticleRepository {
    override fun getArticles(): Articles {
        return Articles(driver.getArticles().map { Article(it.slug, it.title, it.body, it.updated) })
    }

    override fun getArticle(slug: String): Article {
        return driver.getArticle(slug).let { Article(it.slug, it.title, it.body, it.updated) }
    }
}