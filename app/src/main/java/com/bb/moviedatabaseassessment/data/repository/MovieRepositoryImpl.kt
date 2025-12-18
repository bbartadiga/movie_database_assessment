package com.bb.moviedatabaseassessment.data.repository

import com.bb.moviedatabaseassessment.data.remote.TmdbApi
import com.bb.moviedatabaseassessment.domain.model.Movie
import com.bb.moviedatabaseassessment.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val api: TmdbApi
) : MovieRepository {
    override suspend fun getPopularMovies(page: Int): List<Movie> {
        return api.getPopularMovies(page = page).results.map { dto ->
            Movie(
                id = dto.id,
                title = dto.title.orEmpty(),
                posterPath = dto.posterPath,
                releaseDate = dto.releseDate,
                voteAverage = dto.voteAverage,
                overview = dto.overview
            )
        }
    }

}

