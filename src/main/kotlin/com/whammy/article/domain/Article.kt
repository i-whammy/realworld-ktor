package com.whammy.article.domain

import java.time.LocalDateTime

data class Article(
    val slug: String,
    val title: String,
    val body: String,
    val authorEmailAddress: String,
    val createdAt: LocalDateTime,
    val comments: Comments,
    val favorites: List<Favorite>
) {
    companion object {
        // TODO care about duplicated slug
        fun of(authorEmailAddress: String, title: String, body: String): Article =
            Article(title.replace(" ", "-"), title, body, authorEmailAddress, LocalDateTime.now(), Comments(emptyList()), emptyList())
    }

    fun toggleFavoriteFrom(emailAddress: String): Article {
        val newFavorites = if (this.favorites.contains(Favorite(emailAddress))) this.favorites.minus(Favorite(emailAddress)) else this.favorites.plus(Favorite(emailAddress))
        return Article(this.slug, this.title, this.body, this.authorEmailAddress, this.createdAt, this.comments, newFavorites)
    }
}
