package com.bb.moviedatabaseassessment.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoListResponseDto(
    val id: Int,
    val results: List<VideoDto>
)