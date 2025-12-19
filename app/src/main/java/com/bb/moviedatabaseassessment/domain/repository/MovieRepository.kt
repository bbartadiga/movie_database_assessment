package com.bb.moviedatabaseassessment.domain.repository

import android.provider.MediaStore
import com.bb.moviedatabaseassessment.domain.model.Genre
import com.bb.moviedatabaseassessment.domain.model.Movie
import com.bb.moviedatabaseassessment.domain.model.MovieDetail
import com.bb.moviedatabaseassessment.domain.model.MoviePage
import com.bb.moviedatabaseassessment.domain.model.Review
import com.bb.moviedatabaseassessment.domain.model.Video

interface MovieRepository {

    suspend fun getMovieDetail(movieId: Int): MovieDetail
    suspend fun getMovieReviews(movieId: Int, page: Int): MoviePage<Review>
    suspend fun getMovieVideos(movieId: Int): List<Video>

}