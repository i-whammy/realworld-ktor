package com.whammy.article.repository

import com.whammy.article.repository.ArticleModel

interface ArticleDriver {
    fun getArticles(): List<ArticleModel>
    fun getArticle(slug: String): ArticleModel?
    fun getCommentsOfArticle(slug: String): List<CommentModel>
}
