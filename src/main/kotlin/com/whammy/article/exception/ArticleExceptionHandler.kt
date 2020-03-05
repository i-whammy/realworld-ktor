package com.whammy.article.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ArticleExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler( ArticleNotFoundException::class )
    @ResponseBody
    fun handleArticleNotFoundException(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hashMapOf(Pair("message", "No Article Found.")))
    }
}