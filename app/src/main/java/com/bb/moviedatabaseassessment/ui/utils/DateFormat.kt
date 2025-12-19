package com.bb.moviedatabaseassessment.ui.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private val fmt = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.US)

fun formatIsoDate(iso: String?): String? = try {
    iso?.let { fmt.format(Instant.parse(it).atZone(ZoneId.systemDefault())) }
} catch (_: Exception) {
    iso
}