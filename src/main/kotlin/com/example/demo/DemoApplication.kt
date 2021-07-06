package com.example.demo

import com.toedter.spring.hateoas.jsonapi.JsonApiConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.hateoas.config.EnableHypermediaSupport
import org.springframework.web.reactive.config.WebFluxConfigurer

@SpringBootApplication
@EnableHypermediaSupport(type = [])
class DemoApplication : WebFluxConfigurer {

    @Bean
    fun jsonApiConfiguration(): JsonApiConfiguration? {
        return JsonApiConfiguration()
    }
}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
