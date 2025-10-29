package com.example.learnify.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learnify.R
import com.example.learnify.ui.theme.PrimaryColor
import com.example.learnify.ui.theme.SecondaryColor

@Composable
fun LearnifyHeader(
    modifier: Modifier = Modifier,
) {

    Surface(
        color = Color.Transparent,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.learnify_icon),
                    contentDescription = "Learnify Icon",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(end = 6.dp)
                )
                Text(
                    text = "Learnify",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            LearnifySearchBar ()
        }
    }
}
