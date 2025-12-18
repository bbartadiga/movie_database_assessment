package com.bb.moviedatabaseassessment.ui.preview

import com.bb.moviedatabaseassessment.domain.model.Genre
import com.bb.moviedatabaseassessment.domain.model.Movie
import com.bb.moviedatabaseassessment.domain.model.Review

object SampleData {

    val genres = listOf(
        Genre(id = 1, name = "Action"),
        Genre(id = 2, name = "Comedy"),
        Genre(id = 3, name = "Drama"),
        Genre(id = 4, name = "Horror"),
        Genre(id = 5, name = "Romance"),
    )

    val movies = listOf(
        Movie(
            id = 1,
            title = "Sample Movie A",
            posterPath = null,
            releaseDate = "2024-01-01",
            voteAverage = 7.8,
            overview = "Overview of Sample Movie A."
        ),
        Movie(
            id = 2,
            title = "Sample Movie B",
            posterPath = null,
            releaseDate = "2023-01-01",
            voteAverage = 6.5,
            overview = "Overview of Sample Movie B."
        ),
        Movie(
            id = 3,
            title = "Sample Movie C",
            posterPath = null,
            releaseDate = "2022-01-01",
            voteAverage = 8.2,
            overview = "Overview of Sample Movie C."
        )
    )

    val reviews = listOf(
        Review(id = "r1", author = "User1", content = "Great movie! I loved every minute of it."),
        Review(id = "r2", author = "User2", content = "Not my cup of tea."),
        Review(id = "r3", author = "User3", content = "This movie is a masterpiece.")
    )
}
