package com.bb.moviedatabaseassessment.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoDto(
    val id: String,
    val key: String?,
    val name: String?,
    val site: String?,
    val type: String?,
    val official: Boolean?,
    @Json(name = "published_at") val publishedAt: String?
)