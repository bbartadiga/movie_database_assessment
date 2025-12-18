package com.bb.moviedatabaseassessment.domain.repository

import com.bb.moviedatabaseassessment.domain.model.Genre
import com.bb.moviedatabaseassessment.domain.model.Movie

interface MovieRepository {
    suspend fun getPopularMovies(page: Int =  1): List<Movie>

}