package com.example.demo

import org.springframework.hateoas.EntityModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class DemoController {

    @GetMapping("/mono-entity-model/{id}", produces = ["application/vnd.api+json"])
    fun monoEntityModel(@PathVariable id: String, @RequestParam type: String): Mono<EntityModel<Payload>> {
        val payload = Payload(id = id, type = type, message = "mono entity model")
        return Mono.just(EntityModel.of(payload))
    }

    @GetMapping("/coroutine-entity-model/{id}", produces = ["application/vnd.api+json"])
    suspend fun coroutineEntityModel(@PathVariable id: String, @RequestParam type: String): EntityModel<Payload> {
        val payload = Payload(id = id, type = type, message = "coroutine entity model")
        return EntityModel.of(payload)
    }

    @GetMapping("/mono-response-entity/{id}", produces = ["application/vnd.api+json"])
    fun monoResponseEntityModel(@PathVariable id: String, @RequestParam type: String): Mono<ResponseEntity<EntityModel<Payload>>> {
        val payload = Payload(id = id, type = type, message = "mono response entity")
        return Mono.just(ResponseEntity.ok(EntityModel.of(payload)))
    }

    @GetMapping("/coroutine-response-entity/{id}", produces = ["application/vnd.api+json"])
    suspend fun coroutineResponseEntityModel(@PathVariable id: String, @RequestParam type: String): ResponseEntity<EntityModel<Payload>> {
        val payload = Payload(id = id, type = type, message = "coroutine response entity")
        return ResponseEntity.ok(EntityModel.of(payload))
    }

}

