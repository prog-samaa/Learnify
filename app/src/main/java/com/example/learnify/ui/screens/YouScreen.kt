package com.example.learnify.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.learnify.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YouScreen(navController: NavController, viewModel: UserViewModel) {

    val user = viewModel.currentUser.value
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Profile") }
            )
        }
    ) { padding ->

        if (user == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Loading...")
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(Color(0xFFF8F7FF))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFD9C3FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user.name.first().uppercase(),
                        color = Color.White
                    )
                }

                Spacer(Modifier.height(16.dp))
                Text(user.name, style = MaterialTheme.typography.headlineSmall)
                Text(user.email, color = Color.Gray)

                Spacer(Modifier.height(20.dp))

                Button(onClick = { showDialog = true }) {
                    Text("Edit Profile")
                }

                Spacer(Modifier.height(40.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    onClick = {
                        viewModel.logout()
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                ) {
                    Text("Logout", color = Color.White)
                }
            }
        }

        if (showDialog && user != null) {
            EditProfileDialog(
                currentName = user.name,
                currentPhone = user.phone,
                onDismiss = { showDialog = false },
                onSave = { name: String, phone: String ->
                    viewModel.updateUser(name, phone)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun EditProfileDialog(
    currentName: String,
    currentPhone: String,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var name by remember { mutableStateOf(currentName) }
    var phone by remember { mutableStateOf(currentPhone) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = { onSave(name, phone) }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
        title = { Text("Edit Profile") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone") }
                )
            }
        }
    )
}
