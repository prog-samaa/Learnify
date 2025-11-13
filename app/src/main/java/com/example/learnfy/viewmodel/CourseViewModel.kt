package com.example.learnfy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnfy.model.CourseEntity
import com.example.learnfy.repository.CourseRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CourseViewModel(private val repo: CourseRepository) : ViewModel() {
    private val _course = MutableStateFlow<CourseEntity?>(null)
    val course: StateFlow<CourseEntity?> = _course.asStateFlow()

    fun loadCourse(id: String) {
        viewModelScope.launch {
            repo.getCourse(id).collect {
                _course.value = it
            }
        }
    }

    fun toggleLike() = update { repo.toggleLike(it) }
    fun toggleWatchLater() = update { repo.toggleWatchLater(it) }
    fun toggleCheck() = update { repo.toggleCheck(it) }

    private fun update(action: suspend (CourseEntity) -> Unit) {
        viewModelScope.launch {
            _course.value?.let { action(it) }
        }
    }

    fun insert(course: CourseEntity) {
        viewModelScope.launch {
            repo.insert(course)
        }
    }
}