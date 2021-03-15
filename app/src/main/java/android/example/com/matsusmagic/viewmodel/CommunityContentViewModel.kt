package android.example.com.matsusmagic.viewmodel

import android.app.Application
import android.example.com.matsusmagic.model.ChannelModel
import android.example.com.matsusmagic.model.YouTubeApiService
import android.example.com.matsusmagic.model.YouTubeVideo
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CommunityContentViewModel(application: Application): BaseViewModel(application) {

    val playListData = MutableLiveData<List<YouTubeVideo>>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    private val youTubeService = YouTubeApiService()
    private val youTubeList = mutableListOf<YouTubeVideo>()
    private val disposable = CompositeDisposable()
    private val playlistIds = arrayListOf(
        "UULsiaNUb42gRAP7ewbJ0ecQ",
        "UUh76d0-ff5eaN68V2OaoghA",
        "UU4eoJsIkD_w_mTIUFONJ7RA",
        "UUCpEfhS6csLp1S-mFrJrnSA",
        "UUB07ZExZqnxwfApgxJW9-qg",
        "UUum8N4KUUC0l_NK_mybvilg",
        "UU4xxpoHPvCDqfuh9qbkCH4Q"
    )

    fun refresh() {
        getYouTubeVideos()
    }

    private fun getYouTubeVideos() {
        loading.value = true
        youTubeList.clear()
        for (playlistId in playlistIds) {
            disposable.add(
                youTubeService.getChannelPlaylist(playlistId)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<ChannelModel>() {
                        override fun onSuccess(t: ChannelModel) {
                            t.items.forEach { youTubeList.add(it) }
                            if (youTubeList.size == playlistIds.size * 5) {
                                    youTubeList.sortByDescending { it.snippet?.publishedAt }
                                    loading.value = false
                                    playListData.value = youTubeList
                            }
                        }

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                            loading.value = false
                            error.value = true
                        }

                    })
            )
        }

    }
}