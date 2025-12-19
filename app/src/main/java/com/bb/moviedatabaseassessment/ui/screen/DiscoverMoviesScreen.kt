package com.bb.moviedatabaseassessment.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bb.moviedatabaseassessment.domain.model.Genre
import com.bb.moviedatabaseassessment.domain.model.Movie
import com.bb.moviedatabaseassessment.ui.utils.posterUrl
import com.bb.moviedatabaseassessment.ui.viewmodel.DiscoverUiState
import com.bb.moviedatabaseassessment.ui.viewmodel.DiscoverViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverMoviesScreen(
    genre: Genre,
    vm: DiscoverViewModel,
    onBack: () -> Unit,
    onMovieClick: (Movie) -> Unit
) {
    val state by vm.uiState.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(genre.id) { vm.loadFirst(genre.id) }

    // endless scroll
    LaunchedEffect(listState, state) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisible ->
                val total = listState.layoutInfo.totalItemsCount
                val shouldLoadMore = lastVisible != null && lastVisible >= total - 4
                if (shouldLoadMore) vm.loadMore()
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Discover: ${genre.name}") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }
            )
        }
    ) { padding ->

        when (val s = state) {
            is DiscoverUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(padding))
            }

            is DiscoverUiState.Error -> {
                Text("Error: ${s.message}", modifier = Modifier.padding(padding))
            }

            is DiscoverUiState.Success -> {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    items(s.movies) { movie ->
                        val ratingText = movie.voteAverage?.toString() ?: "-"
                        val dateText = movie.releaseDate ?: "-"
                        val url = posterUrl(movie.posterPath)

                        ListItem(
                            headlineContent = { Text(movie.title) },
                            supportingContent = { Text("Rating: $ratingText • $dateText") },
                            leadingContent = {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(url)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Poster",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                    .size(width = 56.dp, height = 84.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                            },
                            modifier = Modifier
                                .clickable { onMovieClick(movie) }
                                .fillMaxWidth()
                        )
                        HorizontalDivider()
                    }

                    if (s.isLoadingMore) {
                        item {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }
        }
    }
}
