package com.example.learnify.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnify.data.repository.CourseRepository
import com.example.learnify.data.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CourseViewModel(
    private val repository: CourseRepository = CourseRepository()
) : ViewModel() {

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
                _courses.value = response.items
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
                _courses.value = response.items
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Unexpected error"
            } finally {
                _isLoading.value = false
            }
        }
    }


}