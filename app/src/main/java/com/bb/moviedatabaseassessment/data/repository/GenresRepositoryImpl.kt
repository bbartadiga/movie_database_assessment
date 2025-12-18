package com.bb.moviedatabaseassessment.data.repository

import com.bb.moviedatabaseassessment.data.remote.TmdbApi
import com.bb.moviedatabaseassessment.domain.model.Genre
import com.bb.moviedatabaseassessment.domain.repository.GenresRepository

class GenresRepositoryImpl(
    private val api: TmdbApi
) : GenresRepository {

    override suspend fun getGenres(): List<Genre> {
        return api.getMovieGenres().genres.map { dto ->
            Genre(id = dto.id, name = dto.name)
        }
    }
}