package android.example.com.matsusmagic.viewmodel

import android.app.Application
import android.example.com.matsusmagic.model.*
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class CardViewModel(application: Application): BaseViewModel(application) {


    val cardLiveData = MutableLiveData<Card>()
    val cardRulingsData = MutableLiveData<Rulings>()
    val decksLiveData = MutableLiveData<List<Decks>>()
    private val cardsService = CardsApiService()
    private val disposable = CompositeDisposable()

    fun fetchCard(cardId: String) {
        disposable.add(
            cardsService.getCard(cardId)
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
            cardsService.getRulings(cardId)
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
            val decksRetrieved = CardDatabase(getApplication()).cardDao().getDecks()
            decksLiveData.value = decksRetrieved
        }
    }
    fun createDeck(deckName: String) {
        launch {
            val deck = Decks(0, deckName,0,0.0, 0.0)
            CardDatabase(getApplication()).cardDao().addDeck(deck)
        }
    }
    fun addCardToDeck(card: Card, deck: String) {
        launch {
            val cardQuery = CardDatabase(getApplication()).cardDao().getCard(card.card_id)
            if (cardQuery == null) CardDatabase(getApplication()).cardDao().addCard(card)
            val deckId = CardDatabase(getApplication()).cardDao().getDeck(deck).deckId
            val register = CardsInDecks(0, deckId, card.card_id,false)
            CardDatabase(getApplication()).cardDao().addCardInDeck(register)
            card.prices?.usd?.let {
                CardDatabase(getApplication()).cardDao().updatePriceInsertUs(deckId,
                    it
                )
            }
            card.prices?.tix?.let {
                CardDatabase(getApplication()).cardDao().updatePriceInsertTix(deckId,
                    it
                )
            }
            CardDatabase(getApplication()).cardDao().updateQuantityInsert(deckId)
            }
    }
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}