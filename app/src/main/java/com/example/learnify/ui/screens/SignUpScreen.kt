package com.example.learnify.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.learnify.viewmodel.UserViewModel

@Composable
fun SignUpScreen(navController: NavController, viewModel: UserViewModel) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Sign Up",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone") })
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { viewModel.register(name, email, phone, password) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCFB0F7))
        ) {
            Text("Register")
        }

        TextButton(onClick = { navController.navigate("login") }) {
            Text("Already have an account? Login", color = Color.Black)
        }

        viewModel.errorMessage.value?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        if (viewModel.isSuccess.value) {
            Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
            navController.navigate("login") {
                popUpTo("signup") { inclusive = true }
            }
        }
    }
}