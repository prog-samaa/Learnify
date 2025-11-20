package com.example.learnify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.learnify.ui.components.BottomNavigation
import com.example.learnify.ui.screens.*
import com.example.learnify.viewmodel.UserViewModel
import com.example.learnify.ui.theme.LearnifyTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnifyTheme {
                val navController = rememberNavController()
                val viewModel: UserViewModel = viewModel()
                var selected by remember { mutableStateOf<String?>(null) }

                // 🔹 راقب الشاشة الحالية
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // 🔹 تحقق من حالة تسجيل الدخول
                val currentUser = FirebaseAuth.getInstance().currentUser
                val showBottomBar = currentUser != null &&
                        currentRoute !in listOf("login", "signup", "forgot")

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavigation(
                                navController = navController,
                                onHomeClicked = { selected = null }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = if (currentUser == null) "login" else "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // 🔸 شاشات المصادقة
                        composable("login") { LoginScreen(navController, viewModel) }
                        composable("signup") { SignUpScreen(navController, viewModel) }
                        composable("forgot") { ForgotPasswordScreen(navController, viewModel) }

                        // 🔸 شاشات التطبيق
                        composable("home") {
                            HomeScreen(
                                selected = selected,
                                onSelect = { selected = it },
                                onHomeClicked = { selected = null }
                            )
                        }
                        composable("pomodoro") { PomodoroScreen() }
                        composable("todo") { ToDoScreen() }
                        composable("you") { YouScreen(navController, viewModel) }
                    }
                }
            }
        }
    }
}
