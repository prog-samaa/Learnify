package com.example.learnfy.ui

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.learnfy.viewmodel.CourseViewModel

@Composable
fun CourseDetailsScreen(courseId: String, viewModel: CourseViewModel) {
    val context = LocalContext.current
    val course by viewModel.course.collectAsState()


    LaunchedEffect(courseId) {
        viewModel.loadCourse(courseId)
    }

    course?.let { c ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEBEBF8))
                .padding(16.dp)
        ) {
            AsyncImage(
                model = c.thumbnailUrl,
                contentDescription = c.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = c.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(2f),
                    color = Color.Black
                )

                IconButton(onClick = {
                    viewModel.toggleLike()
                    Toast.makeText(context, "Toggled Favorite", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Like",
                        tint = if (c.isLiked) Color.Red else Color.Gray
                    )
                }

                IconButton(onClick = {
                    viewModel.toggleWatchLater()
                    Toast.makeText(context, "Toggled Watch Later", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        imageVector = Icons.Default.AccessTimeFilled,
                        contentDescription = "Watch Later",
                        tint = if (c.isWatchLater) Color(0xFF7232BE) else Color.Gray
                    )
                }

                Checkbox(
                    checked = c.isChecked,
                    onCheckedChange = {
                        viewModel.toggleCheck()
                        Toast.makeText(context, "Toggled Completion", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            Text("By ${c.channelTitle}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Text(c.description, style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(c.youtubeUrl))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }, colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFA96DF3),
                contentColor = Color.White
            )) {
                Text("Watch on YouTube")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}