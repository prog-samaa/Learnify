package com.example.learnify.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
    CategoryName: String, QueryGrid: String, TrendingChannelId: String
) {
    var searchQuery by remember { mutableStateOf("") }

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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = CategoryName,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = PrimaryColor,
                            modifier = Modifier.weight(1f),
                            fontFamily = FontFamily(Font(R.font.playwrite))

                        )
                        Image(
                            painter = painterResource(id = R.drawable.category_screen_icon),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    LearnifySearchBar(
                        onSearch = { query ->
                            searchQuery = query
                        })
                    Spacer(Modifier.height(9.dp))
                    Row(modifier = Modifier.padding(start = 8.dp)) {
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
            }

            item {
                CourseRowScreen(
                    cardWeight = 245,
                    cardHeight = 300,
                    query = TrendingChannelId,
                    isTrending = true
                )
            }

            item {
                CourseGridScreen(
                    cardWeight = 240,
                    cardHeight = 285,
                    query = if (searchQuery.isBlank()) QueryGrid else searchQuery,
                    isSearch = searchQuery.isNotBlank()
                )
            }


            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}
