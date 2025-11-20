package com.example.learnify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import com.example.learnify.data.model.User
import com.example.learnify.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val repo = UserRepository()

    val isSuccess = mutableStateOf(false)
    val isLoggedIn = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    val currentUser = mutableStateOf<User?>(null)

    init {
        observeCurrentUser()
    }

    fun register(name: String, email: String, phone: String, password: String) {
        viewModelScope.launch {
            try {
                val success = repo.registerUser(name, email, phone, password)
                isSuccess.value = success
                if (success) isLoggedIn.value = true
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val success = repo.loginUser(email, password)
                isLoggedIn.value = success
                if (success) observeCurrentUser()
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }

    private fun observeCurrentUser() {
        val uid = repo.getCurrentUser()?.uid ?: return

        repo.listenToUserRealtime(uid) { data ->
            if (data != null) {
                currentUser.value = User(
                    uid = uid,
                    name = data["name"] as? String ?: "",
                    email = data["email"] as? String ?: "",
                    phone = data["phone"] as? String ?: "",
                    imageUrl = data["imageUrl"] as? String ?: "",
                    watchlist = data["watchlist"] as? List<String> ?: emptyList(),
                    favorites = data["favorites"] as? List<String> ?: emptyList()
                )
            }
        }
    }

    fun resetPassword(email: String) {
        repo.resetPassword(email)
    }

    fun updateUser(name: String, phone: String) {
        val uid = repo.getCurrentUser()?.uid ?: return
        val updates = mapOf(
            "name" to name,
            "phone" to phone
        )

        viewModelScope.launch {
            try {
                repo.updateUserFields(uid, updates)
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }

    fun logout() {
        repo.logout()
        isLoggedIn.value = false
        currentUser.value = null
    }
}
