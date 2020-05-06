package com.whammy.article.di

import com.whammy.article.driver.InMemoryArticleDriver
import com.whammy.article.repository.ArticleDriver
import com.whammy.article.repository.ArticleRepository
import com.whammy.article.usecase.ArticleUsecase
import com.whammy.article.usecase.IArticleRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val articleDependency = Kodein {
    bind<ArticleDriver>() with singleton { InMemoryArticleDriver() }
    bind<IArticleRepository>() with singleton { ArticleRepository(instance()) }
    bind<ArticleUsecase>() with singleton { ArticleUsecase(instance()) }
}