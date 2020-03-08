package com.whammy.article.usecase

import com.whammy.article.domain.Article
import com.whammy.article.domain.Articles
import com.whammy.article.domain.Comment
import com.whammy.article.domain.Comments
import org.springframework.stereotype.Service

@Service
class ArticleUsecase(private val repository: IArticleRepository) {
    fun getArticlesOrderedByUpdatedDateTime(): Articles {
        return repository.getArticles().sortByUpdatedAt()
    }

    fun getArticle(slug: String): Article {
        return repository.getArticle(slug)
    }

    fun getCommentsOfArticle(slug: String): Comments {
        return repository.getCommentsOfArticle(slug)
    }

    fun addComment(slug: String, body: String): Comment {
        val comments = repository.getCommentsOfArticle(slug)
        val newComments = comments.add(body)
        repository.saveComments(slug, newComments)
        return newComments.getLatestComment()
    }
}
