package com.example.learnfy.repository

import com.example.learnfy.data.CourseDao
import com.example.learnfy.model.CourseEntity
import kotlinx.coroutines.flow.Flow

class CourseRepository(private val dao: CourseDao) {
    fun getCourse(id: String): Flow<CourseEntity?> = dao.getCourse(id)

    suspend fun toggleLike(course: CourseEntity) {
        dao.update(course.copy(isLiked = !course.isLiked))
    }

    suspend fun toggleWatchLater(course: CourseEntity) {
        dao.update(course.copy(isWatchLater = !course.isWatchLater))
    }

    suspend fun toggleCheck(course: CourseEntity) {
        dao.update(course.copy(isChecked = !course.isChecked))
    }

    suspend fun insert(course: CourseEntity) = dao.insert(course)
}