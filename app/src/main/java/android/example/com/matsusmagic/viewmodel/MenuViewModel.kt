package android.example.com.matsusmagic.viewmodel

import android.app.Application
import android.example.com.matsusmagic.model.*
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MenuViewModel(application: Application) : BaseViewModel(application) {

    val cardLiveData = MutableLiveData<Card>()
    val playListData = MutableLiveData<List<YouTubeVideo>>()
    val id = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Boolean>()
    private val cardsService = CardsApiService()
    private val youTubeService = YouTubeApiService()
    private val disposable = CompositeDisposable()
    private val youTubeList = mutableListOf<YouTubeVideo>()
    private val playlistIds = arrayListOf(
        "UULsiaNUb42gRAP7ewbJ0ecQ",
        "UUh76d0-ff5eaN68V2OaoghA",
        "UU4eoJsIkD_w_mTIUFONJ7RA",
        "UUCpEfhS6csLp1S-mFrJrnSA",
        "UUB07ZExZqnxwfApgxJW9-qg",
        "UUum8N4KUUC0l_NK_mybvilg",
        "UU4xxpoHPvCDqfuh9qbkCH4Q"
    )

    fun getCommander() {
        disposable.add(
            cardsService.getCardOfTheDay()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Card>() {
                    override fun onSuccess(t: Card) {
                        cardLiveData.value = t


                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    fun getCommanderByCachedId(id: String) {
        disposable.add(
            cardsService.getCard(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Card>() {
                    override fun onSuccess(t: Card) {
                        cardLiveData.value = t

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    fun getYouTubePlaylist() {
        isLoading.value = true
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
                                isLoading.value = false
                                playListData.value = youTubeList.take(3)
                            }
                        }

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                            isLoading.value = false
                            isError.value = true
                        }

                    })
            )
        }
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}