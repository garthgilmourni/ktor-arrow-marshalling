package com.example.model

import arrow.core.Either
import arrow.core.Option
import arrow.core.nonEmptySetOf
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.option

object BookStore {
    private val stock = nonEmptySetOf(
        Book("Permutation City", nonEmptySetOf("Greg Egan"), Genre.Scifi) to 1,
        Book("Project Hail Mary", nonEmptySetOf("Andy Weir"), Genre.Scifi) to 2,
        Book("The Three-Body Problem", nonEmptySetOf("Cixin Liu"), Genre.Scifi) to 15,
        Book("This is How You Lose the Time War", nonEmptySetOf("Amal El-Mohtar", "Max Gladstone"), Genre.Scifi) to 0,
        Book("A Problem from Hell", nonEmptySetOf("Samantha Power"), Genre.History) to 8,
        Book("When Genius Failed", nonEmptySetOf("Roger Lowenstein"), Genre.History) to 6,
        Book("Brexit Unfolded", nonEmptySetOf("Chris Grey"), Genre.History) to 0,
        Book("Lila", nonEmptySetOf("Robert Pirsig"), Genre.Philosophy) to 0,
        Book("The Sleepwalkers", nonEmptySetOf("Arthur Koestler"), Genre.Philosophy) to 7
    )

    fun findByGenre(genre: Genre): Either<String, List<Book>> = either {
        val results = stock.filter { it.first.genre == genre }
        ensure(results.isNotEmpty()) {
            "No books in genre $genre"
        }
        results.map { it.first }
    }

    fun findByTitle(title: String): Option<Either<String, Book>> = option {
        val result = stock.find {
            it.first.title == title
        }

        ensureNotNull(result)
        either {
            val(book, numInStock) = result
            ensure(numInStock > 0) {
                "${book.title} is out of stock"
            }
            book
        }
    }
}