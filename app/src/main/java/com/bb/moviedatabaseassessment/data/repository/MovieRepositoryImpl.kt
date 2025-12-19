package com.bb.moviedatabaseassessment.data.repository

import com.bb.moviedatabaseassessment.data.remote.TmdbApi
import com.bb.moviedatabaseassessment.domain.model.MovieDetail
import com.bb.moviedatabaseassessment.domain.model.MoviePage
import com.bb.moviedatabaseassessment.domain.model.Review
import com.bb.moviedatabaseassessment.domain.model.Video
import com.bb.moviedatabaseassessment.domain.repository.MovieRepository


class MovieRepositoryImpl(
    private val api: TmdbApi
) : MovieRepository {

    override suspend fun getMovieDetail(movieId: Int): MovieDetail {
        val dto = api.getMovieDetails(movieId)
        return MovieDetail(
            id = dto.id,
            title = dto.title.orEmpty(),
            overview = dto.overview.orEmpty(),
            posterPath = dto.posterPath,
            backdropPath = dto.backdropPath,
            releaseDate = dto.releaseDate,
            voteAverage = dto.voteAverage,
            runtime = dto.runtime
        )
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int): MoviePage<Review> {
        val res = api.getMovieReviews(movieId, page)
        return MoviePage(
            page = res.page,
            totalPages = res.totalPages,
            results = res.results.map {
                Review(
                    id = it.id,
                    author = it.author.orEmpty(),
                    content = it.content.orEmpty(),
                    createdAt = it.createdAt
                )
            }
        )
    }

    override suspend fun getMovieVideos(movieId: Int): List<Video> {
        val res = api.getMovieVideos(movieId)
        return res.results.map {
            Video(
                id = it.id,
                key = it.key.orEmpty(),
                name = it.name.orEmpty(),
                site = it.site.orEmpty(),
                type = it.type.orEmpty()
            )
        }
    }
}
