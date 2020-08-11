package ru.netology.route

import io.ktor.application.call
import io.ktor.features.NotFoundException
import io.ktor.features.ParameterConversionException
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import org.kodein.di.generic.instance
import org.kodein.di.ktor.kodein
import ru.netology.dto.PostRequestDto
import ru.netology.dto.PostResponseDto
import ru.netology.model.PostModel
import ru.netology.repository.PostRepository

fun Routing.v1() {
    route("/") {
        get {
            call.respondText("Hello!!!", ContentType.Text.Plain)
        }
    }

    route("/api/v1/posts") {
        val repo by kodein().instance<PostRepository>()

        get {
            val response = repo.getAll().map { PostResponseDto.fromModel(it) }
            call.respond(response)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw ParameterConversionException("id", "Int")
            val model = repo.getById(id) ?: throw NotFoundException()
            val response = PostResponseDto.fromModel(model)
            call.respond(response)
        }

        post {
            val post = call.receive<PostRequestDto>()
            val model = PostModel(post.id, post.author, post.content, postType = post.postType)
            val newPost = repo.save(model)
            val response = PostResponseDto.fromModel(newPost)
            call.respond(response)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw ParameterConversionException("id", "Int")
            repo.removeById(id)
            call.respond(HttpStatusCode.NoContent)
        }
    }

    route("/api/v1/repost") {}
}

//curl -H "Content-Type: application/json" -d '{ "id": 2, "author": "kate", "content": "test"}' -X POST http://127.0.0.1:8080/api/v1/posts
