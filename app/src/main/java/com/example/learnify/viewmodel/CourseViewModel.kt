package com.example.learnify.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnify.data.AppDatabase
import com.example.learnify.data.model.Course
import com.example.learnify.data.model.CourseEntity
import com.example.learnify.data.model.PlaylistItem
import com.example.learnify.data.repository.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CourseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CourseRepository

    init {
        val courseDao = AppDatabase.Companion.getDatabase(application).courseDao()
        repository = CourseRepository(courseDao)
    }

    // --- API Courses ---
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun searchCourses(query: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.searchCourses(query)
                _courses.value = response.items.map { it.toCourse() }
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Unexpected error"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getPlaylistsByChannel(channelId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.getPlaylistsByChannel(channelId)
                _courses.value = response.items.map { it.toCourse() }
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Unexpected error"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // --- Local DB CourseEntity ---
    private val _course = MutableStateFlow<CourseEntity?>(null)
    val course: StateFlow<CourseEntity?> = _course.asStateFlow()

    fun loadCourse(id: String) {
        viewModelScope.launch {
            repository.getCourse(id).collect {
                _course.value = it
            }
        }
    }

    fun toggleLike() = update { repository.toggleLike(it) }
    fun toggleWatchLater() = update { repository.toggleWatchLater(it) }
    fun toggleCheck() = update { repository.toggleCheck(it) }

    private fun update(action: suspend (CourseEntity) -> Unit) {
        viewModelScope.launch {
            _course.value?.let { action(it) }
        }
    }

    fun insert(course: CourseEntity) {
        viewModelScope.launch {
            repository.insert(course)
        }
    }

    private fun PlaylistItem.toCourse(): Course {
        return Course(
            id = this.id.playlistId,
            title = this.details.courseTitle,
            description = this.details.courseDescription,
            channelTitle = this.details.channelTitle,
            thumbnailUrl = this.details.imageUrl.thumbnail.url,
            youtubeUrl = "https://www.youtube.com/playlist?list=${this.id.playlistId}",
            rating = this.rating
        )
    }
}