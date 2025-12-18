package com.bb.moviedatabaseassessment.domain.repository

import com.bb.moviedatabaseassessment.domain.model.Movie
import com.bb.moviedatabaseassessment.domain.model.MoviePage

interface DiscoverRepository {
    suspend fun discoverMoviesByGenre(
        genreId: Int,
        page: Int = 1
    ): MoviePage<Movie>
}