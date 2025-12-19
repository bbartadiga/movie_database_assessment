package com.bb.moviedatabaseassessment.ui.utils

fun posterUrl(path: String?, size: String = "w500"): String? =
    path?.takeIf { it.isNotBlank() }?.let { "https://image.tmdb.org/t/p/$size$it" }