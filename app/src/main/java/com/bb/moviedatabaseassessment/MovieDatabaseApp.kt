package com.bb.moviedatabaseassessment

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bb.moviedatabaseassessment.data.remote.NetworkModule
import com.bb.moviedatabaseassessment.data.repository.DiscoverRepositoryImpl
import com.bb.moviedatabaseassessment.data.repository.GenresRepositoryImpl
import com.bb.moviedatabaseassessment.domain.model.Genre
import com.bb.moviedatabaseassessment.domain.model.Movie
import com.bb.moviedatabaseassessment.ui.screen.DiscoverMoviesScreen
import com.bb.moviedatabaseassessment.ui.screen.GenresScreen
import com.bb.moviedatabaseassessment.ui.screen.MovieDetailScreen
import com.bb.moviedatabaseassessment.ui.theme.MovieDatabaseAssessmentTheme
import com.bb.moviedatabaseassessment.ui.viewmodel.DiscoverViewModel
import com.bb.moviedatabaseassessment.ui.viewmodel.DiscoverViewModelFactory
import com.bb.moviedatabaseassessment.ui.viewmodel.GenresViewModel
import com.bb.moviedatabaseassessment.ui.viewmodel.GenresViewModelFactory

@Composable
fun MovieDatabaseApp() {
    MovieDatabaseAssessmentTheme {
        Surface(modifier = Modifier.fillMaxSize()) {

            var selectedGenre by remember { mutableStateOf<Genre?>(null) }
            var selectedMovie by remember { mutableStateOf<Movie?>(null) }

            val genresRepo = GenresRepositoryImpl(NetworkModule.tmdbApi)
            val genresVm: GenresViewModel =
                viewModel(factory = GenresViewModelFactory(genresRepo))

            val discoverRepo = DiscoverRepositoryImpl(NetworkModule.tmdbApi)
            val discoverVm: DiscoverViewModel =
                viewModel(factory = DiscoverViewModelFactory(discoverRepo))

            when {
                selectedGenre == null -> {
                    GenresScreen(
                        vm = genresVm,
                        onGenreClick = {
                            selectedGenre = it
                            selectedMovie = null
                        }
                    )
                }

                selectedMovie == null -> {
                    DiscoverMoviesScreen(
                        genre = selectedGenre!!,
                        vm = discoverVm,
                        onBack = {
                            selectedGenre = null
                            selectedMovie = null
                        },
                        onMovieClick = { selectedMovie = it }
                    )
                }

                else -> {
                    MovieDetailScreen(
                        movie = selectedMovie!!,
                        onBack = { selectedMovie = null }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppPreview() {
    MovieDatabaseApp()
}