package android.example.com.matsusmagic.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DecksDao {

    @Query("SELECT * FROM Decks")
    suspend fun getDecks() : MutableList<Decks>

    @Insert
    suspend fun addDeck (deck: Decks)

    @Query("SELECT * FROM Decks WHERE deckName IS :deckName")
    suspend fun getDeck(deckName: String) : Decks

    @Query ("DELETE FROM decks WHERE deckId IS :deckId")
    suspend fun deleteDeck(deckId: Int)

    @Query ("UPDATE decks SET totalPriceUsd = totalPriceUsd - :cardPrice WHERE deckId IS :deckId ")
    suspend fun updatePriceDeleteUs (deckId: Int, cardPrice: Double)

    @Query ("UPDATE decks SET totalPriceTix = totalPriceTix - :cardPrice WHERE deckId IS :deckId ")
    suspend fun updatePriceDeleteTix (deckId: Int, cardPrice: Double)

    @Query ("UPDATE decks SET cardQuantity = cardQuantity - 1 WHERE deckId IS :deckId ")
    suspend fun updateQuantityDelete (deckId: Int)

    @Query ("UPDATE decks SET totalPriceUsd = totalPriceUsd + :cardPrice WHERE deckId IS :deckId ")
    suspend fun updatePriceInsertUs (deckId: Int, cardPrice: Double)

    @Query ("UPDATE decks SET totalPriceTix = totalPriceTix + :cardPrice WHERE deckId IS :deckId ")
    suspend fun updatePriceInsertTix (deckId: Int, cardPrice: Double)

    @Query ("UPDATE decks SET cardQuantity = cardQuantity + 1 WHERE deckId IS :deckId ")
    suspend fun updateQuantityInsert (deckId: Int)

}