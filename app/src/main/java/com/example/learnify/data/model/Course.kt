package com.example.learnify.data.model

data class Course(
    val id: String,
    val title: String,
    val description: String,
    val channelTitle: String,
    val thumbnailUrl: String,
    val youtubeUrl: String,
    val rating: Float? = null
)