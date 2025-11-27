package com.example.learnify.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.learnify.ui.components.CourseCard
import com.example.learnify.ui.components.Loading
import com.example.learnify.ui.theme.AppBackgroundColor
import com.example.learnify.ui.theme.Purple40
import com.example.learnify.viewmodel.UserViewModel
import com.example.learnify.viewmodel.CourseViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YouScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    courseViewModel: CourseViewModel
) {
    val user = userViewModel.currentUser.value
    if (user == null) {
        Loading()
        return
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Profile",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(AppBackgroundColor)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ---------- Profile Image ----------
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFD9C3FF)),
                contentAlignment = Alignment.Center
            ) {
                if (user.imageUrl.isNotEmpty()) {
                    AsyncImage(
                        model = user.imageUrl,
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                } else {
                    Text(
                        text = user.name.first().uppercase(),
                        color = Color.White,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            Text(
                user.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(user.email, color = Color.Gray)
            Text(user.phone, color = Color.Gray)

            Spacer(Modifier.height(24.dp))

            // Edit Profile Button
            Button(
                onClick = { navController.navigate("edit_profile") },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Edit Profile", fontWeight = FontWeight.Medium)
            }

            Spacer(Modifier.height(24.dp))

            // ---------- Favorites Section ----------
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Section Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Liked Courses",
                            tint = Color(0xFF6650a4),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Liked Courses",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    if (user.favorites.isEmpty()) {
                        Text(
                            "No liked courses yet",
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    } else {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(user.favorites) { courseId ->
                                // تحميل بيانات الكورس
                                var course by remember { mutableStateOf<com.example.learnify.data.model.Course?>(null) }

                                LaunchedEffect(courseId) {
                                    courseViewModel.loadCourse(courseId)
                                    // الانتظار لفترة قصيرة قبل ما اجيب البيانات
                                    delay(100)
                                    val courseEntity = courseViewModel.course.value
                                    course = courseEntity?.let { entity ->
                                        com.example.learnify.data.model.Course(
                                            id = entity.id,
                                            title = entity.title,
                                            description = entity.description,
                                            channelTitle = entity.channelTitle,
                                            thumbnailUrl = entity.thumbnailUrl,
                                            youtubeUrl = entity.youtubeUrl,
                                            rating = 4f
                                        )
                                    }
                                }

                                course?.let {
                                    CourseCard(
                                        course = it,
                                        cardWeight = 160,
                                        cardHeight = 200,
                                        modifier = Modifier
                                            .width(160.dp)
                                            .clickable {
                                                navController.navigate("course_details/$courseId")
                                            }
                                    )
                                } ?: run {
                                    // عرض placeholder أثناء التحميل
                                    Box(
                                        modifier = Modifier
                                            .width(160.dp)
                                            .height(200.dp)
                                            .background(Color.LightGray, RoundedCornerShape(12.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ---------- Watchlist Section ----------
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Section Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.WatchLater,
                            contentDescription = "Watch Later",
                            tint = Color(0xFF6650a4),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Watch Later",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    if (user.watchlist.isEmpty()) {
                        Text(
                            "No courses in watchlist",
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    } else {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(user.watchlist) { courseId ->
                                // تحميل بيانات الكورس
                                var course by remember { mutableStateOf<com.example.learnify.data.model.Course?>(null) }

                                LaunchedEffect(courseId) {
                                    courseViewModel.loadCourse(courseId)
                                    // الانتظار لفترة قصيرة قبل ما اجيب البيانات
                                    delay(100)
                                    val courseEntity = courseViewModel.course.value
                                    course = courseEntity?.let { entity ->
                                        com.example.learnify.data.model.Course(
                                            id = entity.id,
                                            title = entity.title,
                                            description = entity.description,
                                            channelTitle = entity.channelTitle,
                                            thumbnailUrl = entity.thumbnailUrl,
                                            youtubeUrl = entity.youtubeUrl,
                                            rating = 4f
                                        )
                                    }
                                }

                                course?.let {
                                    CourseCard(
                                        course = it,
                                        cardWeight = 160,
                                        cardHeight = 200,
                                        modifier = Modifier
                                            .width(160.dp)
                                            .clickable {
                                                navController.navigate("course_details/$courseId")
                                            }
                                    )
                                } ?: run {
                                    // عرض placeholder أثناء التحميل
                                    Box(
                                        modifier = Modifier
                                            .width(160.dp)
                                            .height(200.dp)
                                            .background(Color.LightGray, RoundedCornerShape(12.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Logout Button
            Button(
                onClick = {
                    userViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Logout", color = Color.White, fontWeight = FontWeight.Medium)
            }
        }
    }
}