package com.example.learnify.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.learnify.data.model.Course
import com.example.learnify.ui.theme.ActiveStar
import com.example.learnify.ui.theme.AppBackgroundColor
import com.example.learnify.ui.theme.unActiveStar

@Composable
fun CourseCard(
    course: Course,
    cardWeight: Int,
    cardHeight: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(cardWeight.dp)
            .height(cardHeight.dp)
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackgroundColor),
            horizontalAlignment = Alignment.Start
        ) {
            SubcomposeAsyncImage(
                model = course.thumbnailUrl,
                contentDescription = "Course Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            ) {
                SubcomposeAsyncImageContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleY = 1.39f
                            translationY = -6f
                        }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {

                Text(
                    text = course.title,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )
                Text(
                    text = "By ${course.channelTitle}",
                    fontSize = 8.sp,
                    color = Color.Gray,
                    maxLines = 1
                )

                Row {
                    val rating = course.rating ?: 4f
                    Log.d("CourseRating", "Course ${course.title} -> $rating")

                    repeat(5) { index ->
                        val tint =
                            if (index < rating.toInt()) ActiveStar else unActiveStar
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = tint,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}