package com.bb.moviedatabaseassessment.domain.repository

import com.bb.moviedatabaseassessment.domain.model.Genre

interface GenresRepository {
    suspend fun getGenres(): List<Genre>
}