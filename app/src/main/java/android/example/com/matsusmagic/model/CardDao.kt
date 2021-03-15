package android.example.com.matsusmagic.model



import androidx.room.*

@Dao
interface CardDao {

    @Query("SELECT * FROM CardToSearch WHERE name LIKE '%' || :cardname || '%'")
    suspend fun getCardIds(cardname: String) : MutableList<CardToSearch>

    @Query ("SELECT * FROM Decks")
    suspend fun getDecks() : MutableList<Decks>

    @Insert
    suspend fun addDeck (deck: Decks)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCard(card: Card)

    @Query("SELECT * FROM Decks WHERE deckName IS :deckName")
    suspend fun getDeck(deckName: String) : Decks

    @Query ("SELECT * FROM Card WHERE card_id is :cardId")
    suspend fun getCard(cardId: String): Card?

    @Insert
    suspend fun addCardInDeck(cardsInDecks: CardsInDecks)

    @Query ("SELECT cardId FROM cardsindecks WHERE deckId is :deckId")
    suspend fun getCardsFromDeck(deckId: Int): MutableList<String>

    @Query("SELECT * FROM cardsindecks WHERE cardId IS :cardId")
    suspend fun getCardRecords(cardId: String): MutableList<CardsInDecks>

    @Query ("DELETE FROM cardsindecks WHERE cardId IS :cardId AND deckId IS :deckId")
    suspend fun deleteCardFromDeck(cardId: String, deckId: Int)

    @Query("DELETE FROM card WHERE card_id IS :cardId")
    suspend fun deleteCard(cardId: String)

    @Query ("DELETE FROM decks WHERE deckId IS :deckId")
    suspend fun deleteDeck(deckId: Int)

    @Query ("DELETE FROM cardsindecks WHERE deckId IS :deckId")
    suspend fun deleteDeckRecords(deckId: Int)

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