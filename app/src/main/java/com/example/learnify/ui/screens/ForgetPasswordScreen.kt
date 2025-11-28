package com.example.learnify.ui.screens

import com.example.learnify.viewmodel.UserViewModel
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ForgotPasswordScreen(navController: NavController, viewModel: UserViewModel) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }

    val purpleButton = Color(0xFF6650a4)
    val purpleText = Color.White
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Forgot Password", style = MaterialTheme.typography.headlineLarge, color = Color.Black)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Enter your Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (email.isNotEmpty()) {
                    viewModel.resetPassword(email)
                    Toast.makeText(
                        context,
                        "Reset link sent! Check your email.",
                        Toast.LENGTH_LONG
                    ).show()
                    navController.navigate("login")
                } else {
                    Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = purpleButton)
        ) {
            Text("Send Reset Link", color = purpleText)
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(onClick = { navController.navigate("login") }) {
            Text("Back to Login", color = purpleButton)
        }
    }
}
