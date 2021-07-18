package android.example.com.matsusmagic.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CardsInDecksDao {

    @Insert
    suspend fun addCardInDeck(cardsInDecks: CardsInDecks)

    @Query("SELECT cardId FROM cardsindecks WHERE deckId is :deckId")
    suspend fun getCardsFromDeck(deckId: Int): MutableList<String>

    @Query("SELECT * FROM cardsindecks WHERE cardId IS :cardId")
    suspend fun getCardRecords(cardId: String): MutableList<CardsInDecks>

    @Query("DELETE FROM cardsindecks WHERE cardId IS :cardId AND deckId IS :deckId")
    suspend fun deleteCardFromDeck(cardId: String, deckId: Int)

    @Query ("DELETE FROM cardsindecks WHERE deckId IS :deckId")
    suspend fun deleteDeckRecords(deckId: Int)
}