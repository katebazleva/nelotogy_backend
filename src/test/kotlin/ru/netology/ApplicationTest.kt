package ru.netology

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.junit.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun `test get all`() {
        withTestApplication({ module() }) {
            with(handleRequest(HttpMethod.Get, "/users")) {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}
