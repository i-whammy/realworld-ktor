package com.whammy.article

interface ArticleDriver {
    fun getArticles(): List<ArticleModel>
    fun getArticle(slug: String): ArticleModel
}
