package com.example.learnify.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learnify.R
import com.example.learnify.ui.components.CategoryButton
import com.example.learnify.ui.components.CourseRowScreen
import com.example.learnify.ui.components.LearnifyHeader
import com.example.learnify.ui.theme.AppBackgroundColor
import com.example.learnify.ui.theme.PrimaryColor

@Composable
fun HomeScreen(
    selected: String?,
    onSelect: (String) -> Unit,
    onHomeClicked: () -> Unit
) {
    val TrendingProgrammingChannelId = "UC8butISFwT-Wl7EV0hUK0BQ"
    val TrendingMedicalChannelId = "UCNI0qOojpkhsUtaQ4_2NUhQ"
    val TrendingEngineeringChannelId = "UClqhvGmHcvWL9w3R48t9QXQ"
    val TrendingMarketingChannelId = "UCaAx1xeTgF3rs4rBPDq6-Kw"
    val TrendingLanguageChannelId = "UCu8Lth4FT5HxaP0nypE-gTQ"
    val TrendingHumanDevelopmentChannelId = "UCtYzVCmNxrshH4_bPO_-Y-A"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .background(AppBackgroundColor)

    ) {
        if (selected == null) {

            LearnifyHeader()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(11.dp)
            ) {
                Text(
                    text = "Categories",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor
                )
                Image(
                    painter = painterResource(id = R.drawable.category_icon),
                    contentDescription = "category Icon",
                    modifier = Modifier
                        .size(37.dp)
                        .padding(end = 7.dp)
                )
            }
            CategoryButton(selected = selected, onSelect = onSelect)
            CourseRowScreen(query = "Courses")

        } else {
            when (selected) {
                "Programming" -> CategoryScreen("Programming Core","programming courses",TrendingProgrammingChannelId)
                "Engineering" -> CategoryScreen("Engineering Core","Engineering courses",TrendingEngineeringChannelId)
                "Medical" ->     CategoryScreen("Medical Core","medical courses",TrendingMedicalChannelId)
                "Marketing" ->   CategoryScreen("Marketing Core","marketing courses",TrendingMarketingChannelId)
                "Language" ->    CategoryScreen("Language Core","language courses",TrendingLanguageChannelId)
                "Human Development" -> CategoryScreen("Human Development Core","Human Development Courses",TrendingHumanDevelopmentChannelId)
            }
        }
    }
}
