package com.example.learnfy.data

import androidx.room.*
import com.example.learnfy.model.CourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course: CourseEntity)

    @Query("SELECT * FROM user_courses WHERE id = :id")
    fun getCourse(id: String): Flow<CourseEntity?>

    @Update
    suspend fun update(course: CourseEntity)
}