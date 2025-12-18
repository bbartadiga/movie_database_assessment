package com.bb.moviedatabaseassessment.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.bb.moviedatabaseassessment.domain.model.Genre
import com.bb.moviedatabaseassessment.ui.viewmodel.GenresUiState
import com.bb.moviedatabaseassessment.ui.viewmodel.GenresViewModel
import androidx.compose.runtime.getValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenresScreen(
    vm: GenresViewModel,
    onGenreClick: (Genre) -> Unit
) {
    val state by vm.uiState.collectAsState()
    LaunchedEffect(Unit) { vm.load() }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Genres") }) }
    ) { padding ->
        when (val s = state) {
            is GenresUiState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(padding))
            is GenresUiState.Error -> Text("Error: ${s.message}", modifier = Modifier.padding(padding))
            is GenresUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    items(s.genres) { g ->
                        ListItem(
                            headlineContent = { Text(g.name) },
                            modifier = Modifier.clickable { onGenreClick(g) }
                        )
                    }
                }
            }
        }
    }
}