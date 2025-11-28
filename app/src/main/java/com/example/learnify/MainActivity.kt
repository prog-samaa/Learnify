package com.example.learnify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.learnify.ui.screens.HomeScreen
import com.example.learnify.ui.screens.PomodoroScreen
import com.example.learnify.ui.screens.ToDoScreen
import com.example.learnify.ui.screens.YouScreen
import com.example.learnify.ui.theme.LearnifyTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.navigation.compose.composable
import com.example.learnify.ui.components.BottomNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnifyTheme {
                val navController = rememberNavController()
                var selected by remember { mutableStateOf<String?>(null) }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Bottom)),
                    bottomBar = {
                        BottomNavigation(
                            navController = navController,
                            onHomeClicked = { selected = null }
                        )

                    }
                )  { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            HomeScreen(
                                selected = selected,
                                onSelect = { selected = it },
                                onHomeClicked = { selected = null }
                            )
                        }
                        composable("pomodoro") { PomodoroScreen() }
                        composable("todo") { ToDoScreen() }
                        composable("you") { YouScreen() }
                    }
                }
            }
        }
    }
}