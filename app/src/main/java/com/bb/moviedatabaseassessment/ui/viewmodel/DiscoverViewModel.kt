package com.bb.moviedatabaseassessment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bb.moviedatabaseassessment.domain.model.Movie
import com.bb.moviedatabaseassessment.domain.repository.DiscoverRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface DiscoverUiState {
    data object Loading : DiscoverUiState
    data class Success(
        val genreId: Int,
        val movies: List<Movie>,
        val page: Int,
        val totalPages: Int,
        val isLoadingMore: Boolean = false
    ) : DiscoverUiState

    data class Error(val message: String) : DiscoverUiState
}

class DiscoverViewModel(
    private val repo: DiscoverRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DiscoverUiState>(DiscoverUiState.Loading)
    val uiState: StateFlow<DiscoverUiState> = _uiState

    fun loadFirst(genreId: Int) {
        viewModelScope.launch {
            _uiState.value = DiscoverUiState.Loading
            runCatching { repo.discoverMoviesByGenre(genreId = genreId, page = 1) }
                .onSuccess { page ->
                    _uiState.value = DiscoverUiState.Success(
                        genreId = genreId,
                        movies = page.results,
                        page = page.page,
                        totalPages = page.totalPages
                    )
                }
                .onFailure { _uiState.value = DiscoverUiState.Error(it.message ?: "Unknown Error") }
        }
    }

    fun loadMore() {
        val current = _uiState.value as? DiscoverUiState.Success ?: return
        if (current.isLoadingMore) return
        if (current.page >= current.totalPages) return

        viewModelScope.launch {
            _uiState.value = current.copy(isLoadingMore = true)
            runCatching { repo.discoverMoviesByGenre(genreId = current.genreId, page = current.page + 1) }
                .onSuccess { next ->
                    val merged = current.movies + next.results
                    _uiState.value = current.copy(
                        movies = merged,
                        page = next.page,
                        totalPages = next.totalPages,
                        isLoadingMore = false
                    )
                }
                .onFailure {
                    _uiState.value = current.copy(isLoadingMore = false)
                }
        }
    }
}
