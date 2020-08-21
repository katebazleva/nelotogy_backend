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
import ru.netology.model.PostType
import ru.netology.model.Video
import ru.netology.repository.PostRepository
import ru.netology.repository.PostRepositoryInMemoryImpl
import ru.netology.route.v1
import java.util.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

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
            PostRepositoryInMemoryImpl().apply {
                runBlocking {
                    save(
                        PostModel(
                            0,
                            "author",
                            "test",
                            Date(),
                            postType = PostType.VIDEO_POST,
                            video = Video("https://www.youtube.com/watch?v=1blMRvalEQY")
                        )
                    )
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
