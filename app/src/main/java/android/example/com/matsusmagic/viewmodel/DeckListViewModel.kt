package android.example.com.matsusmagic.viewmodel

import android.app.Application
import android.example.com.matsusmagic.model.Card
import android.example.com.matsusmagic.model.CardDatabase
import android.example.com.matsusmagic.model.CardsInDecks
import android.example.com.matsusmagic.repositories.MainRepo
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch

class DeckListViewModel(private val repo: MainRepo, application: Application) :
    BaseViewModel(application) {

    val cardList = MutableLiveData<List<Card>>()
    val cardsLoadError: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var cards = mutableListOf<Card>()

    fun refresh(deckId: Int) {
        loading.value = true
        getCardsFromRecordDatabase(deckId)
    }

    fun deleteCardFromDeck(card: Card, deckId: Int) {
        launch {
            repo.deleteCardFromDeck(card.card_id, deckId)
            val cardInRecord: MutableList<CardsInDecks> = repo.getCardRecords(card.card_id)
            if (cardInRecord.size == 0) repo.deleteCard(card.card_id)
            card.prices?.usd?.let {
                repo.updatePriceDeleteUs(deckId, it)
            }
            card.prices?.tix?.let {
                repo.updatePriceDeleteTix(deckId, it)
            }
            repo.updateQuantityDelete(deckId)
        }

    }

    private fun getCardsFromRecordDatabase(deckId: Int) {
        launch {
            val cardsToSearch =
                repo.getCardsFromDeck(deckId)
            if (cardsToSearch.isEmpty()) {
                loading.value = false
                cardsLoadError.value = true
            } else {
                getCardsFromCardDatabase(cardsToSearch)
            }
        }
    }

    private fun getCardsFromCardDatabase(cardIds: MutableList<String>) {
        cards.clear()
        for (cardId in cardIds) {
            launch {
                val card = repo.getCardFromDatabase(cardId)
                card?.let {
                    cards.add(it)
                    if (cards.size == cardIds.size) {
                        loading.value = false
                        cardsLoadError.value = false
                        cardList.value = cards
                    }
                }
            }
        }
    }

}
