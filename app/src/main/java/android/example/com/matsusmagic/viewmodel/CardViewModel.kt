package android.example.com.matsusmagic.viewmodel

import android.app.Application
import android.example.com.matsusmagic.model.*
import android.example.com.matsusmagic.repositories.MainRepo
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class CardViewModel(private val repo: MainRepo, application: Application): BaseViewModel(application) {


    val cardLiveData = MutableLiveData<Card>()
    val cardRulingsData = MutableLiveData<Rulings>()
    val decksLiveData = MutableLiveData<List<Decks>>()
    private val disposable = CompositeDisposable()

    fun fetchCard(cardId: String) {
        disposable.add(
            repo.getCard(cardId)
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
        disposable.add(
            repo.getRulings(cardId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Rulings>() {
                    override fun onSuccess(t: Rulings) {
                        cardRulingsData.value = t
                    }
                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                })
        )
    }
    fun getDecks() {
        launch {
            val decksRetrieved = repo.getDecks()
            decksLiveData.value = decksRetrieved
        }
    }
    fun createDeck(deckName: String) {
        launch {
            val deck = Decks(0, deckName,0,0.0, 0.0)
            repo.addDeck(deck)
        }
    }
    fun addCardToDeck(card: Card, deck: String) {
        launch {
            val cardQuery = repo.getCardFromDatabase(card.card_id)
            if (cardQuery == null) repo.addCard(card)
            val deckId = repo.getDeck(deck).deckId
            val register = CardsInDecks(0, deckId, card.card_id,false)
            repo.addCardInDeck(register)
            card.prices?.usd?.let {
                repo.updatePriceInsertUs(deckId, it)
            }
            card.prices?.tix?.let {
                repo.updatePriceInsertTix(deckId, it)
            }
            repo.updateQuantityInsert(deckId)
            }
    }
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}