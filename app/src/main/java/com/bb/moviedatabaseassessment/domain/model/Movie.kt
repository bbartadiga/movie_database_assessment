package com.bb.moviedatabaseassessment.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val year: String,
    val rating: Double,
    val overview: String,
)