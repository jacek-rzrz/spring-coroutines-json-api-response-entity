package com.example.demo

import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.web.server.LocalServerPort
import reactor.netty.http.client.HttpClient


@SpringBootTest(webEnvironment = RANDOM_PORT)
class DemoControllerTests {

    @LocalServerPort
    lateinit var port: String

    @Test
    fun `mono of entity model`() {
        val (status, response) = get("/mono-entity-model/1?type=type-1")

        assertThat(status).isEqualTo(200)
        assertThat(response["data.id"]).isEqualTo("1")
        assertThat(response["data.type"]).isEqualTo("type-1")
        assertThat(response["data.attributes.message"]).isEqualTo("mono entity model")
    }

    @Test
    fun `coroutine and entity model`() {
        val (status, response) = get("/coroutine-entity-model/2?type=type-2")

        assertThat(status).isEqualTo(200)
        assertThat(response["data.id"]).isEqualTo("2")
        assertThat(response["data.type"]).isEqualTo("type-2")
        assertThat(response["data.attributes.message"]).isEqualTo("coroutine entity model")
    }

    @Test
    fun `mono of response entity`() {
        val (status, response) = get("/mono-response-entity/3?type=type-3")

        assertThat(status).isEqualTo(200)
        assertThat(response["data.id"]).isEqualTo("3")
        assertThat(response["data.type"]).isEqualTo("type-3")
        assertThat(response["data.attributes.message"]).isEqualTo("mono response entity")
    }

    @Test
    fun `coroutine and response entity`() {
        val (status, response) = get("/coroutine-response-entity/4?type=type-4")

        assertThat(status).isEqualTo(200)
        assertThat(response["data.id"]).isEqualTo("4")
        assertThat(response["data.type"]).isEqualTo("type-4")
        assertThat(response["data.attributes.message"]).isEqualTo("coroutine response entity")
    }


    private fun get(path: String): Response {
        return HttpClient.create()
            .baseUrl("http://localhost:$port")
            .get().uri(path)
            .responseSingle { response, content ->
                val status = response.status().code()
                content.asString().map { Response(status, it.parse()) }
            }
            .block()!!
    }

    fun String.parse(): JsonContent {
        return JsonContent(JsonPath.parse(this))
    }
}

data class Response(val status: Int, val content: JsonContent)

class JsonContent(private val document: DocumentContext) {
    operator fun get(key: String): String? {
        return document.read("\$.$key")
    }
}
