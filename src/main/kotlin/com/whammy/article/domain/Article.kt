package com.whammy.article.domain

import java.time.LocalDateTime

data class Article(
    val slug: String,
    val title: String,
    val body: String,
    val updatedAt: LocalDateTime,
    // TODO use comments
    val comments: Comments,
    val favorites: List<Favorite>
) {
    fun toggleFavoriteFrom(emailAddress: String): Article {
        if (this.favorites.contains(Favorite(emailAddress))) {
            return Article(this.slug, this.title, this.body, this.updatedAt, this.comments, this.favorites.minus(Favorite(emailAddress)))
        } else {
            return Article(this.slug, this.title, this.body, this.updatedAt, this.comments, this.favorites.plus(Favorite(emailAddress)))
        }
    }
}
