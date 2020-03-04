package com.whammy.article.usecase

import com.whammy.article.domain.Article
import com.whammy.article.domain.Articles

interface IArticleRepository {
    fun getArticles(): Articles
    fun getArticle(slug: String): Article
}
