package com.bb.moviedatabaseassessment.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDto(
    val id: Int,
    val title: String?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releseDate: String?,
    @Json(name = "vote_average") val voteAverage: Double?,
    val overview: String?
)