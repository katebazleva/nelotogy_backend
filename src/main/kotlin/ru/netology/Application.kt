package ru.netology

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.netty.*
import kotlinx.coroutines.runBlocking
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import org.kodein.di.ktor.KodeinFeature
import ru.netology.model.PostModel
import ru.netology.repository.PostRepository
import ru.netology.repository.PostRepositoryMutexImpl
import ru.netology.route.v1
import java.time.LocalDateTime

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module(testing: Boolean = false) {

    install(Routing) {
        v1()
    }

    install(StatusPages) {
        exception<ParameterConversionException> { e ->
            call.respond(HttpStatusCode.BadRequest)
            throw e
        }
        exception<NotFoundException> { e ->
            call.respond(HttpStatusCode.NotFound)
            throw e
        }
        exception<Throwable> { e ->
            call.respond(HttpStatusCode.InternalServerError)
            throw e
        }
    }

    install(KodeinFeature) {
        bind<PostRepository>() with singleton {
            PostRepositoryMutexImpl().apply {
                runBlocking {
                    repeat(10) {
                        save(
                            PostModel(
                                it,
                                "author_$it",
                                "test_$it",
                                LocalDateTime.of(2020, 7, 29, 11, 35, 0)
                            )
                        )
                    }
                }
            }
        }
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            serializeNulls()
        }
    }
}
