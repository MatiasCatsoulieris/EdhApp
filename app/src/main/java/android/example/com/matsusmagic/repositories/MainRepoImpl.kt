package android.example.com.matsusmagic.repositories

import android.example.com.matsusmagic.model.*
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepoImpl(
    private val apiService: CardsApi, private val youTubeService: YouTubeApi,
    private val cardDao: CardDao, private val decksDao: DecksDao,
    private val cardsInDecksDao: CardsInDecksDao, private val cardToSearchDao: CardToSearchDao
) :
    MainRepo {

    override fun getCard(Id: String): Single<Card> = apiService.getCard(Id)

    override fun getRulings(Id: String): Single<Rulings> = apiService.getRulings(Id)

    override fun getCardOfTheDay(): Single<Card> = apiService.getCardOfTheDay()

    override fun getChannelPlaylist(playlistId: String): Single<ChannelModel> =
        youTubeService.getChannelPlaylist(playlistId = playlistId)


    /// Room Database Functions

    override suspend fun getCardIdsFuzzy(cardName: String): MutableList<CardToSearch> =
        withContext(Dispatchers.IO) {
            cardToSearchDao.getCardIdsFuzzy(cardName)
        }

    override suspend fun getCardNames(cardName: String): MutableList<String> =
        withContext(Dispatchers.IO) {
            cardToSearchDao.getCardNames(cardName)
        }

    override suspend fun getDecks(): MutableList<Decks> = withContext(Dispatchers.IO) {
        decksDao.getDecks()
    }

    override suspend fun addDeck(deck: Decks) = withContext(Dispatchers.IO) {
        decksDao.addDeck(deck)
    }

    override suspend fun deleteDeck(deckId: Int) = withContext(Dispatchers.IO) {
        decksDao.deleteDeck(deckId)
    }

    override suspend fun deleteDeckRecords(deckId: Int) = withContext(Dispatchers.IO) {
        cardsInDecksDao.deleteDeckRecords(deckId)
    }

    override suspend fun deleteCardFromDeck(cardId: String, deckId: Int) =
        withContext(Dispatchers.IO) {
            cardsInDecksDao.deleteCardFromDeck(cardId, deckId)
        }

    override suspend fun getCardRecords(cardId: String): MutableList<CardsInDecks> =
        withContext(Dispatchers.IO) {
            cardsInDecksDao.getCardRecords(cardId)
        }

    override suspend fun deleteCard(cardId: String) = withContext(Dispatchers.IO) {
        cardDao.deleteCard(cardId)
    }

    override suspend fun updatePriceDeleteUs(deckId: Int, price: Double) = withContext(Dispatchers.IO) {
        decksDao.updatePriceDeleteUs(deckId, price)
    }

    override suspend fun updatePriceDeleteTix(deckId: Int, price: Double) = withContext(Dispatchers.IO) {
        decksDao.updatePriceDeleteTix(deckId, price)
    }

    override suspend fun updateQuantityDelete(deckId: Int) = withContext(Dispatchers.IO) {
        decksDao.updateQuantityDelete(deckId)
    }

    override suspend fun updatePriceInsertUs(deckId: Int, price: Double) = withContext(Dispatchers.IO) {
        decksDao.updatePriceInsertUs(deckId, price)
    }

    override suspend fun updatePriceInsertTix(deckId: Int, price: Double) = withContext(Dispatchers.IO) {
        decksDao.updatePriceInsertTix(deckId, price)
    }

    override suspend fun updateQuantityInsert(deckId: Int) = withContext(Dispatchers.IO) {
        decksDao.updateQuantityInsert(deckId)
    }

    override suspend fun getCardsFromDeck(deckId: Int): MutableList<String> = withContext(Dispatchers.IO) {
        cardsInDecksDao.getCardsFromDeck(deckId)
    }

    override suspend fun getCardFromDatabase(cardId: String): Card? = withContext(Dispatchers.IO) {
        cardDao.getCard(cardId)
    }

    override suspend fun addCard(cardId: Card) = withContext(Dispatchers.IO) {
        cardDao.addCard(cardId)
    }

    override suspend fun getDeck(deckName: String): Decks = withContext(Dispatchers.IO) {
        decksDao.getDeck(deckName)
    }

    override suspend fun addCardInDeck(register: CardsInDecks) = withContext(Dispatchers.IO) {
        cardsInDecksDao.addCardInDeck(register)
    }

}