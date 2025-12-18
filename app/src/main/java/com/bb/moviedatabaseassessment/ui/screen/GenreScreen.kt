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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bb.moviedatabaseassessment.domain.model.Genre

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenresScreen(
    genres : List<Genre>, onGenreClick: (Genre) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Genres") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(genres) { genre ->
                ListItem(
                    headlineContent = { Text(genre.name) },
                    modifier = Modifier
                        .clickable { onGenreClick(genre) }
                        .fillMaxWidth()
                )
                HorizontalDivider()
            }
        }
    }
}