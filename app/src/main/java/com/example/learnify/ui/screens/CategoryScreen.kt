package com.example.learnify.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learnify.R
import com.example.learnify.ui.components.CourseGridScreen
import com.example.learnify.ui.components.CourseRowScreen
import com.example.learnify.ui.components.LearnifySearchBar
import com.example.learnify.ui.theme.PrimaryColor
import com.example.learnify.ui.theme.BlueBackground

@Composable
fun CategoryScreen(
    CategoryName: String,
    QueryGrid: String,
    QueryRow: String
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                .background(BlueBackground)
                .align(Alignment.TopCenter)
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = CategoryName,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryColor,
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.category_screen_icon),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                LearnifySearchBar()
                Spacer(Modifier.height(9.dp))
                Row (modifier = Modifier.padding(start = 8.dp)){
                    Text(
                        text = "Trending",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryColor,
                    )
                    Image(
                        painter = painterResource(id = R.drawable.trending_icon),
                        contentDescription = "Trending Icon",
                        modifier = Modifier
                            .size(30.dp)
                            .padding(end = 10.dp)
                    )

                }

            }

            CourseRowScreen(query =  QueryRow, isTrending = true)
            CourseGridScreen(240, 285, QueryGrid)

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}
