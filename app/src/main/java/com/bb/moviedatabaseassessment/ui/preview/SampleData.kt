package com.bb.moviedatabaseassessment.ui.preview

import com.bb.moviedatabaseassessment.domain.model.Genre
import com.bb.moviedatabaseassessment.domain.model.Movie
import com.bb.moviedatabaseassessment.domain.model.Review

object SampleData {
    val genres = listOf(
        Genre(1, "Action"),
        Genre(2, "Comedy"),
        Genre(3, "Drama"),
        Genre(4, "Horror"),
        Genre(5, "Romance"),
    )

    val movies = listOf(
        Movie(1, "Sample Movie A", "2024", 7.8, "Overview of Sample Movie A."),
        Movie(2, "Sample Movie B", "2023", 6.5, "Overview of Sample Movie B."),
        Movie(3, "Sample Movie C", "2022", 8.2, "Overview of Sample Movie C."),
        Movie(4, "Sample Movie D", "2021", 5.9, "Overview of Sample Movie D."),
        Movie(5, "Sample Movie E", "2020", 7.1, "Overview of Sample Movie E.")
    )

    val reviews = listOf(
        Review("r1", "User1", "Great movie! I loved every minute of it."),
        Review("r2", "User2", "Not my cup of tea."),
        Review("r3", "User3", "This movie is a masterpiece."),
        Review("r4", "User4", "I'm disappointed with this movie."),
        Review("r5", "User5", "I couldn't put this movie down.")
    )
}