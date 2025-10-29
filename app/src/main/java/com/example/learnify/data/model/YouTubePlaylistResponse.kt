package com.example.learnify.data.model

import com.squareup.moshi.Json

data class YouTubePlaylistResponse(
    @Json(name = "items") val items: List<Course>
)

data class ThumbnailDetail(
    @Json (name = "url") val url : String
)

data class PlaylistThumbnails(
    @Json(name = "high") val thumbnail: ThumbnailDetail

)

data class PlaylistSnippet(
    @Json(name = "title") val courseTitle: String,
    @Json(name = "description") val courseDescription: String,
    @Json(name = "channelTitle") val channelTitle: String,
    @Json(name = "publishTime") val publishTime: String,
    @Json (name = "thumbnails") val imageUrl: PlaylistThumbnails
)

data class PlaylistId(
    @Json(name = "playlistId") val playlistId: String
)

data class Course(
    @Json(name = "snippet") val details: PlaylistSnippet,
    @Json(name = "id") val playlistId: PlaylistId,
    var rating: Float? = null
)

data class PlaylistItemsResponse(
    @Json(name = "items") val items: List<PlaylistVideoItem>
)

data class PlaylistVideoItem(
    @Json(name = "contentDetails") val contentDetails: VideoContentDetails
)

data class VideoContentDetails(
    @Json(name = "videoId") val videoId: String
)

data class VideoStatsResponse(
    @Json(name = "items") val items: List<VideoStatsItem>
)

data class VideoStatsItem(
    @Json(name = "statistics") val statistics: VideoStatistics
)

data class VideoStatistics(
    @Json(name = "viewCount") val viewCount: String,
    @Json(name = "likeCount") val likeCount: String
)
