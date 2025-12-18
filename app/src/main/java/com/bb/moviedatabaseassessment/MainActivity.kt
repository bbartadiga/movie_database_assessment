package com.bb.moviedatabaseassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bb.moviedatabaseassessment.data.remote.NetworkModule
import com.bb.moviedatabaseassessment.data.remote.TmdbApi
import com.bb.moviedatabaseassessment.data.repository.DiscoverRepositoryImpl
import com.bb.moviedatabaseassessment.data.repository.GenresRepositoryImpl
import com.bb.moviedatabaseassessment.data.repository.MovieRepositoryImpl
import com.bb.moviedatabaseassessment.domain.model.Genre
import com.bb.moviedatabaseassessment.domain.model.Movie
import com.bb.moviedatabaseassessment.domain.repository.MovieRepository
import com.bb.moviedatabaseassessment.ui.screen.DiscoverMoviesScreen
import com.bb.moviedatabaseassessment.ui.screen.GenresScreen
import com.bb.moviedatabaseassessment.ui.screen.PopularMoviesScreen
import com.bb.moviedatabaseassessment.ui.theme.MovieDatabaseAssessmentTheme
import com.bb.moviedatabaseassessment.ui.viewmodel.DiscoverViewModel
import com.bb.moviedatabaseassessment.ui.viewmodel.GenresViewModel
import com.bb.moviedatabaseassessment.ui.viewmodel.GenresViewModelFactory
import com.bb.moviedatabaseassessment.ui.viewmodel.PopularMoviesViewModel
import com.bb.moviedatabaseassessment.ui.viewmodel.PopularMoviesViewModelFactory
import retrofit2.Retrofit
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.bb.moviedatabaseassessment.ui.viewmodel.DiscoverViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieDatabaseAssessmentTheme {
                Surface(modifier = Modifier.fillMaxSize()) {

                    var selectedGenre by remember { mutableStateOf<Genre?>(null) }
                    var selectedMovie by remember { mutableStateOf<Movie?>(null) } // nanti task detail

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
                                onGenreClick = { selectedGenre = it }
                            )
                        }

                        selectedMovie == null -> {
                            DiscoverMoviesScreen(
                                genre = selectedGenre!!,
                                vm = discoverVm,
                                onBack = { selectedGenre = null },
                                onMovieClick = { selectedMovie = it }
                            )
                        }

                        else -> {
                            // nanti task #3 detail
                            // MovieDetailScreen(movie = selectedMovie!!, onBack = { selectedMovie = null })
                        }
                    }
                }
            }
        }
    }
}

// screens

//Preview


//Models


//Sample data


