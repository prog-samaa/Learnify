package com.example.learnify.data.network

import com.example.learnify.data.model.ChannelPlaylistResponseModel
import com.example.learnify.data.model.PlaylistItemsResponse
import com.example.learnify.data.model.SearchPlaylistResponseModel
import com.example.learnify.data.model.VideoStatsResponse
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
    ): SearchPlaylistResponseModel

    @GET("playlists")
    suspend fun getChannelPlaylists(
        @Query("part") part: String = "snippet",
        @Query("channelId") channelId: String,
        @Query("maxResults") maxResults: Int = 50,
        @Query("key") apiKey: String
    ): ChannelPlaylistResponseModel

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