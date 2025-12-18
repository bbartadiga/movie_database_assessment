package com.bb.moviedatabaseassessment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bb.moviedatabaseassessment.domain.model.Genre
import com.bb.moviedatabaseassessment.domain.repository.GenresRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface GenresUiState {
    data object Loading : GenresUiState
    data class Success(val genres: List<Genre>) : GenresUiState
    data class Error(val message: String) : GenresUiState
}

class GenresViewModel(
    private val repo: GenresRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<GenresUiState>(GenresUiState.Loading)
    val uiState: StateFlow<GenresUiState> = _uiState

    fun load() = viewModelScope.launch {
        _uiState.value = GenresUiState.Loading
        runCatching { repo.getGenres() }
            .onSuccess { _uiState.value = GenresUiState.Success(it) }
            .onFailure { _uiState.value = GenresUiState.Error(it.message ?: "Unknown error") }
    }
}