package com.bb.moviedatabaseassessment

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.bb.moviedatabaseassessment.domain.model.Genre
import com.bb.moviedatabaseassessment.domain.model.Movie
import com.bb.moviedatabaseassessment.ui.preview.SampleData
import com.bb.moviedatabaseassessment.ui.screen.DiscoverMoviesScreen
import com.bb.moviedatabaseassessment.ui.screen.GenresScreen
import com.bb.moviedatabaseassessment.ui.screen.MovieDetailScreen
import com.bb.moviedatabaseassessment.ui.theme.MovieDatabaseAssessmentTheme

@Composable
fun MovieDatabaseApp() {
    MovieDatabaseAssessmentTheme {
        Surface {
            //navigation
            var selectedGenre by remember { mutableStateOf<Genre?>(null) }
            var selectedMovie by remember { mutableStateOf<Movie?>(null) }

            when {
                selectedGenre == null -> GenresScreen(
                    genres = SampleData.genres,
                    onGenreClick = { selectedGenre = it }
                )

                selectedMovie == null -> DiscoverMoviesScreen(
                    genre = selectedGenre!!,
                    movies = SampleData.movies,
                    onBack = { selectedGenre = null },
                    onMovieClick = { selectedMovie = it }
                )

                else -> MovieDetailScreen(
                    movie = selectedMovie!!,
                    reviews = SampleData.reviews,
                    onBack = { selectedMovie = null }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppPreview() {
    MovieDatabaseApp()
}