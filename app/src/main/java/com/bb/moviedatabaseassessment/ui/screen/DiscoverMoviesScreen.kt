package com.bb.moviedatabaseassessment.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bb.moviedatabaseassessment.domain.model.Genre
import com.bb.moviedatabaseassessment.domain.model.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverMoviesScreen(
    genre: Genre,
    movies: List<Movie>,
    onBack: () -> Unit,
    onMovieClick: (Movie) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Discover: ${genre.name}") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Back") }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(movies) { movie ->
                ListItem(
                    headlineContent = { Text(movie.title) },
                    supportingContent = { Text("Rating: ${movie.rating} • ${movie.year}") },
                    modifier = Modifier
                        .clickable { onMovieClick(movie) }
                        .fillMaxWidth()
                )
                HorizontalDivider()
            }
        }
    }

}