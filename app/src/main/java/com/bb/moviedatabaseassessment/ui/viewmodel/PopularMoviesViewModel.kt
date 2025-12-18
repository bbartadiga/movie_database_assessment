package com.bb.moviedatabaseassessment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bb.moviedatabaseassessment.domain.model.Movie
import com.bb.moviedatabaseassessment.domain.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface PopularUiState  {
    data object Loading : PopularUiState
    data class Success(val movies: List<Movie>) : PopularUiState
    data class Error(val message: String) : PopularUiState
}

class PopularMoviesViewModel(
    private val repo: MovieRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<PopularUiState>(PopularUiState.Loading)
    val uiState: StateFlow<PopularUiState> = _uiState

    fun load() {
        viewModelScope.launch {
            _uiState.value = PopularUiState.Loading
            runCatching { repo.getPopularMovies(page = 1) }
                .onSuccess { _uiState.value = PopularUiState.Success(it) }
                .onFailure { _uiState.value = PopularUiState.Error(it.message ?:
                "Unknown Error") }
        }
    }
}