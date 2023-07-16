@file:UseSerializers(
    EitherSerializer::class,
    OptionSerializer::class
)

package com.example.plugins

import arrow.core.Either
import arrow.core.Option
import arrow.core.serialization.EitherSerializer
import arrow.core.serialization.NonEmptySetSerializer
import arrow.core.serialization.OptionSerializer
import com.example.model.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class TitleSearchResult(val result: Option<Either<String, Book>>)

@Serializable
data class GenreSearchResult(val result: Either<String, List<Book>>)

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
    routing {
        route("/books") {
            get("/byGenre/{genre}") {
                val genreParam = call.parameters["genre"]
                if (genreParam != null) {
                    val genre = Genre.valueOf(genreParam)
                    call.respond(GenreSearchResult(BookStore.findByGenre(genre)))
                    return@get
                }
                call.respond(HttpStatusCode.BadRequest)
            }
            get("/byTitle/{title}") {
                val title = call.parameters["title"]
                if (title != null) {
                    call.respond(TitleSearchResult(BookStore.findByTitle(title)))
                    return@get
                }
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}
