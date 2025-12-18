package com.bb.moviedatabaseassessment.domain.model

data class MoviePage<T>(
    val page: Int,
    val totalPages: Int,
    val results: List<T>
)