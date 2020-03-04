package com.whammy.article

interface IArticleRepository {
    fun getArticles(): Articles
    fun getArticle(slug: String): Article
}
