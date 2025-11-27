package com.example.learnify.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learnify.viewmodel.CourseViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CourseRowScreen(
    cardWeight: Int = 245,
    cardHeight : Int = 300,
    query: String,
    viewModel: CourseViewModel = viewModel(),
    isTrending: Boolean = false
) {
    val courses by viewModel.courses.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(query, isTrending) {
        if (isTrending) {
            viewModel.getPlaylistsByChannel(query)
        } else {
            viewModel.searchCourses(query)
        }
    }

    when {
        isLoading -> Loading()
        error != null -> Loading()
        courses.isEmpty() -> Text("No results found :(", modifier = Modifier.padding(16.dp))
        else -> LazyRow(modifier = Modifier.padding(8.dp)) {
            items(courses) { course ->
                if (isTrending) TrendingCourseCard(course)
                else CourseCard(course = course, cardWeight = cardWeight, cardHeight = cardHeight)
            }
        }
    }
}