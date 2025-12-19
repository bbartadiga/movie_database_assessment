package com.bb.moviedatabaseassessment.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewDto(
    val id: String,
    val author: String?,
    @Json(name = "author_details") val authorDetails: AuthorDetailsDto?,
    val content: String?,
    @Json(name = "created_at") val createdAt: String?,
    @Json(name = "updated_at") val updatedAt: String?,
    val url: String?
)

@JsonClass(generateAdapter = true)
data class AuthorDetailsDto(
    val name: String?,
    val username: String?,
    @Json(name = "avatar_path") val avatarPath: String?,
    val rating: Double?
)