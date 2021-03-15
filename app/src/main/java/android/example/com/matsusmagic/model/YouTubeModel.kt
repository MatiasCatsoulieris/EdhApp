package android.example.com.matsusmagic.model

import com.google.gson.annotations.SerializedName

data class ChannelModel(
    @SerializedName("kind")
    var kind: String?,
    @SerializedName("etag")
    var etag: String?,
    @SerializedName("nextPageToken")
    var nextPageToken: String?,
    @SerializedName("items")
    var items: List<YouTubeVideo>
)

data class YouTubeVideo(
    @SerializedName("kind")
    var kind: String?,
    @SerializedName("etag")
    var etag: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("snippet")
    var snippet: VideoData?
)

data class VideoData(
    @SerializedName("publishedAt")
    var publishedAt: String?,
    @SerializedName("title")
    var title: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("thumbnails")
    var thumbnails: Map<String, Thumbnails>,
    @SerializedName("channelTitle")
    var channelTitle: String,
    @SerializedName("resourceId")
    var resourceId: Map<String, String>
)


data class Thumbnails(
    @SerializedName("url")
    var url: String?
)