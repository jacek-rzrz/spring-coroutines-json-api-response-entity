package com.example.demo

import com.toedter.spring.hateoas.jsonapi.JsonApiId
import com.toedter.spring.hateoas.jsonapi.JsonApiType

public data class Payload public constructor(
    @get:JsonApiId
    val id: String,
    @get:JsonApiType
    val type: String,
    val message: String,
)