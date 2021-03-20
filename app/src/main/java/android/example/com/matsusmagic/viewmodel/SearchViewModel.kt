package android.example.com.matsusmagic.viewmodel

import android.app.Application
import android.example.com.matsusmagic.model.Card
import android.example.com.matsusmagic.model.CardDatabase
import android.example.com.matsusmagic.model.CardToSearch
import android.example.com.matsusmagic.model.CardsApiService
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.util.ArrayList


class SearchViewModel(application: Application): BaseViewModel(application) {

    private val cardsService = CardsApiService()
    private val disposable = CompositeDisposable()

    val cardNames = MutableLiveData<List<String>>()
    val cards = MutableLiveData<List<Card>>()
    val cardsLoadError: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    var cardlist = mutableListOf<Card>()


    fun refresh(cardname: String?) {
        cardlist.clear()
        if (cardname != null && cardname.length >= 3) {
            getFromDataBase(cardname)

        } else {
            Toast.makeText(
                getApplication(),
                "Please enter more than 3 characters",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun getFromDataBase(cardname: String) {
        loading.value = true

        launch {
            val cardsToSearch =
                cardname.let { CardDatabase(getApplication()).cardDao().getCardIds(cardname) }
            if (cardsToSearch.size > 0) {
                fetchFromRemote(cardsToSearch)
            } else {
                cardsLoadError.value = true
            }
        }

    }
    fun getCardNamesForAutoComplete(cardname: String) {
        launch {
            val cardnames =
                cardname.let { CardDatabase(getApplication()).cardDao().getcardNames(cardname) }
            cardNames.value = cardnames
        }
    }

    private fun fetchFromRemote(listOfCards: MutableList<CardToSearch>) {
        cardlist.clear()
        for (cardInfo in listOfCards) {
            disposable.add(
                cardsService.getCard(cardInfo.scryfallId)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<Card>() {
                        override fun onSuccess(card: Card) {
                            cardlist.add(card)
                            if (cardlist.size == listOfCards.size) {
                                loading.value = false
                                cardsLoadError.value = false
                                cards.value = cardlist
                            }


                        }

                        override fun onError(e: Throwable) {

                            cardsLoadError.value = true
                            e.printStackTrace()
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