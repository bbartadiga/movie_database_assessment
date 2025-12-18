package com.bb.moviedatabaseassessment.data.repository

import com.bb.moviedatabaseassessment.data.remote.TmdbApi
import com.bb.moviedatabaseassessment.domain.model.Movie
import com.bb.moviedatabaseassessment.domain.repository.DiscoverRepository
import com.bb.moviedatabaseassessment.domain.model.MoviePage

class DiscoverRepositoryImpl(
    private val api: TmdbApi
) : DiscoverRepository {

    override suspend fun discoverMoviesByGenre(genreId: Int, page: Int): MoviePage<Movie> {
        val res = api.discoverMovies(genreId = genreId, page = page)

        return MoviePage(
            page = res.page,
            totalPages = res.totalPages,
            results = res.results.map { dto ->
                Movie(
                    id = dto.id,
                    title = dto.title.orEmpty(),
                    overview = dto.overview.orEmpty(),
                    releaseDate = dto.releseDate,     // <- sesuai DTO kamu
                    posterPath = dto.posterPath,
                    voteAverage = dto.voteAverage
                )
            }
        )
    }
}