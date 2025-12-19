package com.bb.moviedatabaseassessment.data.remote

import com.bb.moviedatabaseassessment.data.remote.dto.GenresResponseDto
import com.bb.moviedatabaseassessment.data.remote.dto.MovieDetailDto
import com.bb.moviedatabaseassessment.data.remote.dto.MovieDto
import com.bb.moviedatabaseassessment.data.remote.dto.PagedResponseDto
import com.bb.moviedatabaseassessment.data.remote.dto.ReviewDto
import com.bb.moviedatabaseassessment.data.remote.dto.VideoListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi{

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("language") language: String = "en-US"
    ): GenresResponseDto

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): PagedResponseDto<MovieDto>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"
    ): MovieDetailDto

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): PagedResponseDto<ReviewDto>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"
    ): VideoListResponseDto
}