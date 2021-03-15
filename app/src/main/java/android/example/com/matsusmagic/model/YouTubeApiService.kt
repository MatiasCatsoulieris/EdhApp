package android.example.com.matsusmagic.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class YouTubeApiService {

    private val BASE_URL = "https://www.googleapis.com/youtube/v3/"

    private val api = Retrofit.Builder()
        .baseUrl((BASE_URL))
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(YouTubeApi::class.java)

    fun getChannelPlaylist(playlistId: String): Single<ChannelModel> {
        return api.getChannelPlaylist(playlistId = playlistId)
    }
}