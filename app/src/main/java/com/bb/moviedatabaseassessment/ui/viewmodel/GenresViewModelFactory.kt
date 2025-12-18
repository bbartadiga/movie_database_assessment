package com.bb.moviedatabaseassessment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bb.moviedatabaseassessment.domain.repository.GenresRepository

class GenresViewModelFactory(
    private val repo: GenresRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GenresViewModel(repo) as T
    }
}