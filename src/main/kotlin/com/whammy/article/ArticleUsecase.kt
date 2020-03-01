package com.whammy.article

import java.time.LocalDateTime

class ArticleUsecase(private val repository: ArticleRepository) {
    fun getArticles(): Articles {
        return repository.getArticles().sortByUpdatedAt()
    }
}
