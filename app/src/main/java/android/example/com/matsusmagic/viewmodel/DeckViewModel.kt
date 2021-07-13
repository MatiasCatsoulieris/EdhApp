package android.example.com.matsusmagic.viewmodel

import android.app.Application
import android.example.com.matsusmagic.model.CardDatabase
import android.example.com.matsusmagic.model.Decks
import android.example.com.matsusmagic.repositories.MainRepo
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch

class DeckViewModel(private val repo: MainRepo, application: Application): BaseViewModel(application) {

    val decksLiveData = MutableLiveData<List<Decks>>()

    fun retrieveDecks() {
        launch {
            decksLiveData.value = repo.getDecks()
        }
    }

    fun createDeck(deck: Decks) {
        launch {
            repo.addDeck(deck)
        }
    }

    fun deleteDeck(deck: Decks) {
        launch {
            repo.deleteDeck(deck.deckId)
            repo.deleteDeckRecords(deck.deckId)

        }
    }



}
