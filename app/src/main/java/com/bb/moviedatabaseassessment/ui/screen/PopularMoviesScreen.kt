package com.bb.moviedatabaseassessment.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bb.moviedatabaseassessment.domain.model.Movie
import com.bb.moviedatabaseassessment.ui.viewmodel.PopularMoviesViewModel
import com.bb.moviedatabaseassessment.ui.viewmodel.PopularUiState

private fun posterUrl(path: String?): String? {
    if (path.isNullOrBlank()) return null
    return "https://image.tmdb.org/t/p/w342$path"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularMoviesScreen(vm: PopularMoviesViewModel) {
    val state by vm.uiState.collectAsState()
    LaunchedEffect(Unit) { vm.load() }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Popular Movies") }) },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        when (val s = state) {
            is PopularUiState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(innerPadding))
            is PopularUiState.Error -> Text("Error: ${s.message}", modifier = Modifier.padding(innerPadding))
            is PopularUiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(s.movies) { m ->
                        MovieGridItem(movie = m)
                    }
                }
            }
        }
    }

}

@Composable
private fun MovieGridItem(movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
        Column {
            AsyncImage(
                //poster
                model = posterUrl(movie.posterPath),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f)
            )
            //title
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 6.dp)
                    .height(40.dp)
            )
            //date release
            Text(
                text = movie.releaseDate ?: "-",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                    .height(18.dp)
            )
        }

    }
}