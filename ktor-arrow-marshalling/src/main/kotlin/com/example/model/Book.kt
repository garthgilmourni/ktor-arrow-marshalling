@file:UseSerializers(
    NonEmptySetSerializer::class
)

package com.example.model

import arrow.core.*
import arrow.core.serialization.NonEmptySetSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

enum class Genre {
    Scifi,
    Philosophy,
    History,
    Poetry
}

@Serializable
data class Book(val title: String, val authors: NonEmptySet<String>, val genre: Genre)