package com.example.learnify.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.learnify.R
import com.example.learnify.ui.theme.PrimaryColor
import com.example.learnify.ui.theme.SecondaryColor

@Composable
fun LearnifySearchBar(
    onSearch: (String) -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                onSearch(it)
            },
            placeholder = { Text("Search courses...") },
            singleLine = true,
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(16.dp)),
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = PrimaryColor
                )
            },
            colors = TextFieldDefaults.colors(
                disabledContainerColor = SecondaryColor,
                focusedContainerColor = SecondaryColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = PrimaryColor,
            )
        )


        Image(
            painter = painterResource(id = R.drawable.panda_icon),
            contentDescription = "Panda Icon",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(36.dp)
                .offset(x = 10.dp, y = (-4).dp)
                .rotate(12f)
        )
    }
}