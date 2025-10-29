package com.example.learnify.data.network

import com.example.learnify.data.model.PlaylistItemsResponse
import com.example.learnify.data.model.VideoStatsResponse
import com.example.learnify.data.model.YouTubePlaylistResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun searchPlaylists(
        @Query("part") part: String = "snippet",
        @Query("q") query: String,
        @Query("type") type: String = "playlist",
        @Query("maxResults") maxResults: Int = 50,
        @Query("key") apiKey: String
    ): YouTubePlaylistResponse

    @GET("playlists")
    suspend fun getChannelPlaylists(
        @Query("part") part: String = "snippet",
        @Query("channelId") channelId: String,
        @Query("maxResults") maxResults: Int = 50,
        @Query("key") apiKey: String
    ): YouTubePlaylistResponse

    @GET("playlistItems")
    suspend fun getPlaylistItems(
        @Query("part") part: String = "contentDetails",
        @Query("playlistId") playlistId: String,
        @Query("maxResults") maxResults: Int = 1,
        @Query("key") apiKey: String
    ): PlaylistItemsResponse

    @GET("videos")
    suspend fun getVideoStats(
        @Query("part") part: String = "statistics",
        @Query("id") videoId: String,
        @Query("key") apiKey: String
    ): VideoStatsResponse

}