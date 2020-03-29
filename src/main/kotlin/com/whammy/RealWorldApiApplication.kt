package com.whammy

import com.whammy.user.handler.userHandler
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

class RealWorldApiApplication

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080) {
        install(ContentNegotiation) {
            jackson {  }
        }
        routing {
            get("/systems/ping") {
                call.respond(mapOf("message" to "pong"))
            }
            // userHandler(userUsecase)
        }
    }.start(wait = true)
}