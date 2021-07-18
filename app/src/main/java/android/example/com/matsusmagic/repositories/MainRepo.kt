package android.example.com.matsusmagic.repositories

import android.example.com.matsusmagic.model.*
import io.reactivex.Single

interface MainRepo {
    fun getCard(Id: String): Single<Card>

    fun getRulings(Id: String): Single<Rulings>

    fun getCardOfTheDay(): Single<Card>

    fun getChannelPlaylist(playlistId: String): Single<ChannelModel>

    suspend fun getCardIdsFuzzy(cardName: String): MutableList<CardToSearch>

    suspend fun getCardNames(cardName: String): MutableList<String>

    suspend fun getDecks(): MutableList<Decks>

    suspend fun addDeck(decks: Decks)

    suspend fun deleteDeck(deckId: Int)

    suspend fun deleteDeckRecords(deckId: Int)

    suspend fun deleteCardFromDeck(cardId: String, deckId: Int)

    suspend fun getCardRecords(cardId: String): MutableList<CardsInDecks>

    suspend fun deleteCard(cardId: String)

    suspend fun updatePriceDeleteUs(deckId: Int, price: Double)

    suspend fun updatePriceDeleteTix(deckId: Int, price: Double)

    suspend fun updateQuantityDelete(deckId: Int)

    suspend fun updatePriceInsertUs(deckId: Int, price: Double)

    suspend fun updatePriceInsertTix(deckId: Int, price: Double)

    suspend fun updateQuantityInsert(deckId: Int)

    suspend fun getCardsFromDeck(deckId: Int): MutableList<String>

    suspend fun getCardFromDatabase(cardId: String): Card?

    suspend fun addCard(cardId: Card)

    suspend fun getDeck(deck: String): Decks

    suspend fun addCardInDeck(register: CardsInDecks)
}