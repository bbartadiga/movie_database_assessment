package com.bb.moviedatabaseassessment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bb.moviedatabaseassessment.domain.model.MovieDetail
import com.bb.moviedatabaseassessment.domain.model.Review
import com.bb.moviedatabaseassessment.domain.model.Video
import com.bb.moviedatabaseassessment.domain.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class MovieDetailUiState(
    val loading: Boolean = false,
    val detail: MovieDetail? = null,
    val trailer: Video? = null,
    val reviews: List<Review> = emptyList(),
    val page: Int = 1,
    val totalPages: Int = 1,
    val isLoadingMore: Boolean = false,
    val error: String? = null
)

class MovieDetailViewModel(
    private val repo: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MovieDetailUiState())
    val state: StateFlow<MovieDetailUiState> = _state

    private var movieId: Int? = null

    fun load(movieId: Int) {
        this.movieId = movieId
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)

            runCatching { repo.getMovieDetail(movieId) }
                .onSuccess { detail ->
                    _state.value = _state.value.copy(detail = detail)
                }
                .onFailure { e ->
                    _state.value = _state.value.copy(loading = false, error = e.message ?: "Unknown error")
                    return@launch
                }

            runCatching { repo.getMovieVideos(movieId) }
                .onSuccess { videos ->
                    val trailer = videos.firstOrNull {
                        it.site.equals("YouTube", true) && it.type.equals("Trailer", true)
                    }
                    _state.value = _state.value.copy(trailer = trailer)
                }

            // first page reviews
            loadMoreReviews(reset = true)

            _state.value = _state.value.copy(loading = false)
        }
    }

    fun loadMoreReviews(reset: Boolean = false) {
        val id = movieId ?: return
        val current = _state.value
        if (current.isLoadingMore) return

        val nextPage = if (reset) 1 else current.page + 1
        if (!reset && current.page >= current.totalPages) return

        viewModelScope.launch {
            _state.value = current.copy(isLoadingMore = true, error = null)

            runCatching { repo.getMovieReviews(id, nextPage) }
                .onSuccess { pageRes ->
                    _state.value = _state.value.copy(
                        reviews = if (reset) pageRes.results else current.reviews + pageRes.results,
                        page = pageRes.page,
                        totalPages = pageRes.totalPages,
                        isLoadingMore = false
                    )
                }
                .onFailure { e ->
                    _state.value = _state.value.copy(
                        isLoadingMore = false,
                        error = e.message ?: "Unknown error"
                    )
                }
        }
    }
}