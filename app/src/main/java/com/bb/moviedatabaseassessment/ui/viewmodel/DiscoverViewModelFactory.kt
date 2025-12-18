package com.bb.moviedatabaseassessment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bb.moviedatabaseassessment.domain.repository.DiscoverRepository

class DiscoverViewModelFactory(
    private val repo: DiscoverRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DiscoverViewModel(repo) as T
    }
}