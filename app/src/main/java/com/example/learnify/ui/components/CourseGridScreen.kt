package com.example.learnify.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learnify.viewmodel.CourseViewModel

@Composable
fun CourseGridScreen(
    cardWeight: Int = 245,
    cardHeight : Int = 300,
    query: String,
    viewModel: CourseViewModel = viewModel()
) {
    val courses by viewModel.courses.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(query) {
        viewModel.searchCourses(query)
    }

    when {
        isLoading -> Loading()
        error != null -> Loading()
        courses.isEmpty() -> Text("No results found :(")
        else -> LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(courses) { course ->
                CourseCard(course = course, cardWeight = cardWeight, cardHeight = cardHeight)
            }
        }
    }
}
