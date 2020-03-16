package com.whammy.article.repository

import com.whammy.article.domain.Article
import com.whammy.article.domain.Articles
import com.whammy.article.domain.Comment
import com.whammy.article.domain.Comments
import com.whammy.article.exception.ArticleNotFoundException
import com.whammy.article.usecase.IArticleRepository
import org.springframework.stereotype.Repository

@Repository
class ArticleRepository(private val driver: ArticleDriver):
    IArticleRepository {
    override fun getArticles(): Articles {
        return Articles(driver.getArticles().map {
            Article(
                it.slug,
                it.title,
                it.body,
                it.updated,
                // TODO enable to return comment
                emptyList(),
                emptyList()
            )
        })
    }

    override fun getArticle(slug: String): Article {
        return driver.getArticle(slug)?.let { Article(it.slug, it.title, it.body, it.updated, emptyList(), emptyList()) }
            ?: throw ArticleNotFoundException("Article not found. slug = $slug")
    }

    override fun getCommentsOfArticle(slug: String): Comments {
        val article = driver.getArticle(slug) ?: throw ArticleNotFoundException("Article not found. slug = $slug")
        return driver.getCommentsOfArticle(slug).map {
            Comment(it.id, it.body, it.authorEmailAddress, it.createdAt, it.updatedAt)
        }.let { Comments(it) }
    }

    override fun saveComments(slug: String, comments: Comments): Comments {
        driver.saveComments(slug, comments.map {
            CommentModel(it.id, it.body, it.authorEmailAddress, it.createdAt, it.updatedAt)
        })
        return comments
    }

    override fun saveArticle(article: Article): Article {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}