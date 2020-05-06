package com.whammy

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.whammy.article.di.articleDependency
import com.whammy.article.handler.articleHandler
import com.whammy.article.usecase.ArticleUsecase
import com.whammy.user.di.userDependency
import com.whammy.user.handler.userHandler
import com.whammy.user.service.UserService
import com.whammy.user.usecase.UserUsecase
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.kodein.di.generic.instance
import org.kodein.di.newInstance
import java.time.format.DateTimeFormatter

class RealWorldApiApplication

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080) {
        install(ContentNegotiation) {
            val javaTimeModule = JavaTimeModule()
            javaTimeModule.addSerializer(LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")))
            jackson {
                registerModule(javaTimeModule)
            }
        }
        routing {
            get("/systems/ping") {
                call.respond(mapOf("message" to "pong"))
            }
            val userUsecase by userDependency.newInstance { UserUsecase(instance(), instance()) }
            userHandler(userUsecase)
            val articleUsecase by articleDependency.newInstance { ArticleUsecase(instance()) }
            val userService by userDependency.newInstance { UserService() }
            articleHandler(articleUsecase, userService)
        }
    }.start(wait = true)
}