package com.whammy.article.usecase

import com.whammy.article.domain.Article
import com.whammy.article.domain.Articles
import org.springframework.stereotype.Service

@Service
class ArticleUsecase(private val repository: IArticleRepository) {
    fun getArticlesOrderedByUpdatedDateTime(): Articles {
        return repository.getArticles().sortByUpdatedAt()
    }

    fun getArticle(slug: String): Article {
        return repository.getArticle(slug)
    }
}
