package com.example.model

import arrow.core.Either
import arrow.core.Option
import arrow.core.nonEmptySetOf
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.option

import com.example.model.Genre.*

object BookStore {
    private val stock = nonEmptySetOf(
        Book("Permutation City", nonEmptySetOf("Greg Egan"), Scifi) to 1,
        Book("Project Hail Mary", nonEmptySetOf("Andy Weir"), Scifi) to 2,
        Book("The Three-Body Problem", nonEmptySetOf("Cixin Liu"), Scifi) to 15,
        Book("This is How You Lose the Time War", nonEmptySetOf("Amal El-Mohtar", "Max Gladstone"), Scifi) to 0,
        Book("A Problem from Hell", nonEmptySetOf("Samantha Power"), History) to 8,
        Book("When Genius Failed", nonEmptySetOf("Roger Lowenstein"), History) to 6,
        Book("Brexit Unfolded", nonEmptySetOf("Chris Grey"), History) to 0,
        Book("Lila", nonEmptySetOf("Robert Pirsig"), Philosophy) to 0,
        Book("The Sleepwalkers", nonEmptySetOf("Arthur Koestler"), Philosophy) to 7
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