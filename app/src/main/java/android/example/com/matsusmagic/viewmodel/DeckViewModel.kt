package android.example.com.matsusmagic.viewmodel

import android.app.Application
import android.example.com.matsusmagic.model.CardDatabase
import android.example.com.matsusmagic.model.Decks
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch

class DeckViewModel(application: Application): BaseViewModel(application) {

    val decksLiveData = MutableLiveData<List<Decks>>()

    fun retrieveDecks() {
        launch {
            val decksRetrieved = CardDatabase(getApplication()).cardDao().getDecks()
            decksLiveData.value = decksRetrieved
        }
    }

    fun createDeck(deck: Decks) {
        launch {

            CardDatabase(getApplication()).cardDao().addDeck(deck)
        }
    }

    fun deleteDeck(deck: Decks) {
        launch {
            CardDatabase(getApplication()).cardDao().deleteDeck(deck.deckId)
            CardDatabase(getApplication()).cardDao().deleteDeckRecords(deck.deckId)

        }
    }



}
