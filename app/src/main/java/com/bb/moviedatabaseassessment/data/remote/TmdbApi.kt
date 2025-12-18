package com.bb.moviedatabaseassessment.data.remote

import com.bb.moviedatabaseassessment.data.remote.dto.GenresResponseDto
import com.bb.moviedatabaseassessment.data.remote.dto.MovieDto
import com.bb.moviedatabaseassessment.data.remote.dto.PagedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi{
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int =  1,
        @Query("Language") language: String = "en-US"
    ): PagedResponseDto<MovieDto>

    @GET("genre/movie/list")
    suspend fun getMovieGenres(): GenresResponseDto

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): PagedResponseDto<MovieDto>
}