package com.example.learnfy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.learnfy.data.AppDatabase
import com.example.learnfy.repository.CourseRepository
import com.example.learnfy.ui.CourseDetailsScreen
import com.example.learnfy.ui.theme.LearnfyTheme
import com.example.learnfy.viewmodel.CourseViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(this)
        val repo = CourseRepository(db.courseDao())
        val viewModel = CourseViewModel(repo)

        setContent {
            LearnfyTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "details/course1") {
                    composable("details/{courseId}") { backStackEntry ->
                        val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
                        CourseDetailsScreen(courseId = courseId, viewModel = viewModel)
                    }
                }
            }
        }
    }
}