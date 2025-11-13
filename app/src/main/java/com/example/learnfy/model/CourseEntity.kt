package com.example.learnfy.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_courses")
data class CourseEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val channelTitle: String,
    val thumbnailUrl: String,
    val youtubeUrl: String,
    val isLiked: Boolean = false,
    val isWatchLater: Boolean = false,
    val isChecked: Boolean = false
)