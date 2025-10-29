package com.example.learnify.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.learnify.data.model.Course
import com.example.learnify.ui.theme.ActiveStar
import com.example.learnify.ui.theme.unActiveStar

@Composable
fun TrendingCourseCard(course: Course) {
    Card(
        modifier = Modifier
            .width(270.dp)
            .height(200.dp)
            .padding(start = 8.dp, end = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            SubcomposeAsyncImage(
                model = course.details.imageUrl.thumbnail.url,
                contentDescription = "Course Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp))
                    .blur(4.dp),
                contentScale = ContentScale.Crop
            ) {
                SubcomposeAsyncImageContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleY = 1.35f
                            translationY = -6f
                        }
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.4f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            ) {
                Text(
                    text = course.details.courseTitle,
                    fontSize = 12.sp,
                    color = Color.White,
                    maxLines = 1
                )

                Text(
                    text = "By ${course.details.channelTitle}",
                    fontSize = 10.sp,
                    color = Color.LightGray,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row {
                    val rating = course.rating ?: 4f
                    Log.d("CourseRating", "Course ${course.details.courseTitle} -> $rating")

                    repeat(5) { index ->
                        val tint =
                            if (index < rating.toInt()) ActiveStar else unActiveStar
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = tint,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}
