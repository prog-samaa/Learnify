package com.example.learnify.data.repository

import com.example.learnify.data.model.YouTubePlaylistResponse
import com.example.learnify.data.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.learnify.data.CourseDao
import com.example.learnify.data.model.CourseEntity
import kotlinx.coroutines.flow.Flow


class CourseRepository(private val dao: CourseDao) {

    private val apiKey = "Your Api Key"
    fun getCourse(id: String): Flow<CourseEntity?> = dao.getCourse(id)

    suspend fun insert(course: CourseEntity) = dao.insert(course)

    suspend fun toggleLike(course: CourseEntity) {
        dao.update(course.copy(isLiked = !course.isLiked))
    }

    suspend fun toggleWatchLater(course: CourseEntity) {
        dao.update(course.copy(isWatchLater = !course.isWatchLater))
    }

    suspend fun toggleCheck(course: CourseEntity) {
        dao.update(course.copy(isChecked = !course.isChecked))
    }
    suspend fun searchCourses(query: String): YouTubePlaylistResponse {
        return withContext(Dispatchers.IO) {
            val playlists = RetrofitInstance.api.searchPlaylists(
                part = "snippet",
                query = query,
                type = "playlist",
                maxResults = 50,
                apiKey = apiKey
            )

            playlists.copy(
                items = playlists.items.map { playlist ->
                    val rating = calculateRatingForPlaylist(playlist.id.playlistId)
                    playlist.copy(rating = rating)
                }
            )
        }
    }

    private suspend fun calculateRatingForPlaylist(playlistId: String): Float? {
        return try {
            val playlistItems = RetrofitInstance.api.getPlaylistItems(
                playlistId = playlistId,
                apiKey = apiKey
            )

            val firstVideoId = playlistItems.items.firstOrNull()?.contentDetails?.videoId
                ?: return null

            val videoStats = RetrofitInstance.api.getVideoStats(
                videoId = firstVideoId,
                apiKey = apiKey
            )

            val stats = videoStats.items.firstOrNull()?.statistics ?: return null
            val views = stats.viewCount.toFloatOrNull() ?: return null
            val likes = stats.likeCount.toFloatOrNull() ?: 0f

            // لو عدد المشاهدات صغير جدًا، بنرجّع تقييم منخفض لأنه مش موثوق
            if (views < 100) return 1f

            //  تطبيع القيم باستخدام اللوغاريتم لتقليل تأثير الأرقام الكبيرة جدًا
            val logViews = kotlin.math.log10(views + 1)
            val logLikes = kotlin.math.log10(likes + 1)

            //  نحسب نسبة الإعجابات للمشاهدات (مقياس جودة)
            val likeRatio = (likes / views).coerceIn(0f, 1f)

            //  أوزان العوامل
            val popularityWeight = 0.6f  // الشهرة (views)
            val qualityWeight = 0.4f     // الرضا (likes)

            //  تطبيع القيم على نطاق معقول (بناءً على متوسطات شائعة في يوتيوب)
            val normalizedViews = (logViews / 6f).coerceIn(0f, 1f) // فيديو عليه 1M مشاهدة تقريبًا = 6
            val normalizedLikes = ((logLikes / 4f) + likeRatio).coerceIn(0f, 1f)

            //  نحسب التقييم الكلي
            val weightedScore = (normalizedViews * popularityWeight) + (normalizedLikes * qualityWeight)

            //  نحوله لنجوم من 0 إلى 5
            val stars = (weightedScore * 5f).coerceIn(0f, 5f)

            // تقريب لرقم عشري واحد
            String.format("%.1f", stars).toFloat()
        } catch (e: Exception) {
            null
        }
    }
    suspend fun getPlaylistsByChannel(channelId: String): YouTubePlaylistResponse {
        return RetrofitInstance.api.getChannelPlaylists(
            part = "snippet",
            channelId = channelId,
            maxResults = 20,
            apiKey = apiKey
        )
    }
}
