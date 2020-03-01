package com.whammy.article

data class Articles(private val articles: List<Article>) {
    fun sortByUpdatedAt(): Articles {
        return Articles(articles.sortedByDescending { it.updatedAt })
    }
}