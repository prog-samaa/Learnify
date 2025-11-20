package com.example.learnify.data.repository

import com.example.learnify.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val auth = FirebaseAuth.getInstance()
    private val users = FirebaseFirestore.getInstance().collection("users")

    suspend fun registerUser(name: String, email: String, phone: String, password: String): Boolean {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val uid = result.user?.uid ?: return false
        val user = User(uid = uid, name = name, email = email, phone = phone, imageUrl = "")
        users.document(uid).set(user).await()
        return true
    }

    suspend fun loginUser(email: String, password: String): Boolean {
        auth.signInWithEmailAndPassword(email, password).await()
        return auth.currentUser != null
    }

    fun resetPassword(email: String) = auth.sendPasswordResetEmail(email)

    fun getCurrentUser() = auth.currentUser


    suspend fun getUserData(userId: String): Map<String, Any>? {
        val snap = users.document(userId).get().await()
        return if (snap.exists()) snap.data else null
    }

    suspend fun updateUserFields(userId: String, updates: Map<String, Any?>) {
        users.document(userId).update(updates).await()
    }

    suspend fun addToWatchlist(userId: String, courseId: String) {
        users.document(userId).update("watchlist", FieldValue.arrayUnion(courseId)).await()
    }

    suspend fun addToFavorites(userId: String, courseId: String) {
        users.document(userId).update("favorites", FieldValue.arrayUnion(courseId)).await()
    }

    suspend fun removeFromWatchlist(userId: String, courseId: String) {
        users.document(userId).update("watchlist", FieldValue.arrayRemove(courseId)).await()
    }

    suspend fun removeFromFavorites(userId: String, courseId: String) {
        users.document(userId).update("favorites", FieldValue.arrayRemove(courseId)).await()
    }

    fun listenToUserRealtime(userId: String, onChange: (Map<String, Any>?) -> Unit) {
        users.document(userId).addSnapshotListener { snapshot, error ->
            if (error != null) {
                onChange(null)
                return@addSnapshotListener
            }
            onChange(snapshot?.data)
        }
    }

    fun logout() {
        auth.signOut()
    }
}
