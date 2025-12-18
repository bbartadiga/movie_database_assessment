package com.bb.moviedatabaseassessment.ui.viewmodel

import com.bb.moviedatabaseassessment.domain.repository.MovieRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PopularMoviesViewModelFactory(
    private val repo: MovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PopularMoviesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PopularMoviesViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
