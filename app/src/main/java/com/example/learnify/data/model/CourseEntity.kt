// في ملف CourseEntity.kt
package com.example.learnify.data.model

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
) /*{
 CourseEntity إلى Course
    fun toCourse(): Course {
        return Course(
            id = this.id,
            title = this.title,
            description = this.description,
            channelTitle = this.channelTitle,
            thumbnailUrl = this.thumbnailUrl,
            youtubeUrl = this.youtubeUrl,
            rating = 4f
        )
    }
}*/