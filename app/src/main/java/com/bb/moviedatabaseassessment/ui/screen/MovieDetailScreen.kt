package com.bb.moviedatabaseassessment.ui.screen

import android.content.Intent
import android.inputmethodservice.Keyboard.Row
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bb.moviedatabaseassessment.data.remote.NetworkModule
import com.bb.moviedatabaseassessment.data.repository.MovieRepositoryImpl
import com.bb.moviedatabaseassessment.domain.model.Movie
import com.bb.moviedatabaseassessment.ui.viewmodel.MovieDetailViewModel
import com.bb.moviedatabaseassessment.ui.viewmodel.MovieDetailViewModelFactory
import kotlinx.coroutines.flow.distinctUntilChanged
import com.bb.moviedatabaseassessment.ui.utils.formatIsoDate
import com.bb.moviedatabaseassessment.ui.utils.posterUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movie: Movie,
    onBack: () -> Unit
) {
    val repo = MovieRepositoryImpl(NetworkModule.tmdbApi)
    val vm: MovieDetailViewModel = viewModel(factory = MovieDetailViewModelFactory(repo))
    val state by vm.state.collectAsState()

    val context = LocalContext.current
    val listState = rememberLazyListState()

    LaunchedEffect(movie.id) { vm.load(movie.id) }

    // endless scroll reviews
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .distinctUntilChanged()
            .collect { lastVisible ->
                val total = listState.layoutInfo.totalItemsCount
                val shouldLoadMore = lastVisible != null && lastVisible >= total - 3
                if (shouldLoadMore) vm.loadMoreReviews()
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(movie.title.orEmpty()) },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }
            )
        }
    ) { padding ->

        if (state.loading) {
            CircularProgressIndicator(modifier = Modifier.padding(padding))
            return@Scaffold
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                Column(Modifier.padding(16.dp)) {
                    Row(Modifier.fillMaxWidth()) {
                        AsyncImage(
                            model = posterUrl(movie.posterPath, size = "w342"),
                            contentDescription = "Poster",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(140.dp)
                                .aspectRatio(2f / 3f)
                                .clip(RoundedCornerShape(12.dp))
                        )

                        Spacer(Modifier.width(16.dp))

                        Column(Modifier.weight(1f)) {
                            Text(movie.title.orEmpty(), style = MaterialTheme.typography.titleLarge)
                            Spacer(Modifier.height(8.dp))
                            Text("Release: ${movie.releaseDate ?: "-"}")
                            Text("Rating: ${movie.voteAverage ?: "-"}")
                        }
                    }

                    Spacer(Modifier.height(12.dp))
                    Text(movie.overview.orEmpty())

                    Spacer(Modifier.height(12.dp))
                    val trailer = state.trailer
                    if (trailer?.key?.isNotBlank() == true) {
                        TextButton(
                            onClick = {
                                val url = "https://www.youtube.com/watch?v=${trailer.key}"
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                            }
                        ) {
                            Text("Watch trailer (YouTube)")
                        }
                    } else {
                        Text("Trailer: -")
                    }

                    Spacer(Modifier.height(16.dp))
                    Text("User reviews", style = MaterialTheme.typography.titleMedium)
                }
            }

            if (state.error != null) {
                item {
                    Text(
                        text = "Error: ${state.error}",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }

            items(state.reviews) { r ->
                ListItem(
                    overlineContent = { Text(formatIsoDate(r.createdAt) ?: "-") },
                    headlineContent = { Text(r.author) },
                    supportingContent = { Text(r.content) }
                )
                HorizontalDivider()
            }

            if (state.isLoadingMore) {
                item { CircularProgressIndicator(modifier = Modifier.padding(16.dp)) }
            }
        }
    }
}