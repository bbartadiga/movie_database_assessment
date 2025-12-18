package com.bb.moviedatabaseassessment.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bb.moviedatabaseassessment.domain.model.Movie
import com.bb.moviedatabaseassessment.domain.model.Review

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movie: Movie,
    reviews: List<Review>,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(movie.title) },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Back") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Primary info", style = MaterialTheme.typography.titleMedium)
            Text(text = movie.overview)

            HorizontalDivider()

            Text(text = "Reviews", style = MaterialTheme.typography.titleMedium)
            reviews.take(3).forEach { r ->
                Text(text = "• ${r.author}: ${r.content}")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Trailer: nanti link YouTube ditaruh di sini (button/open intent)",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}