package android.example.com.matsusmagic.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApi {
    /* I removed my own Api Key for security reasons.
    *  You will need a YouTube Api Key in order to see
    *  how the YouTube functionality of the app works.
   */
    @GET("playlistItems")
    fun getChannelPlaylist(
        @Query("part") part: String = "snippet",
        @Query("maxResults") maxResults: String = "5",
        @Query("playlistId") playlistId: String,
        @Query("key") key: String = "AIzaSyDIlbhmDEapBN3dpn8z0v6Eg2DaONJDaA0"

    ): Single<ChannelModel>
}