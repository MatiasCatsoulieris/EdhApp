package android.example.com.matsusmagic.model



import androidx.room.*

@Dao
interface CardDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCard(card: Card)

    @Query ("SELECT * FROM Card WHERE card_id is :cardId")
    suspend fun getCard(cardId: String): Card?

    @Query("DELETE FROM card WHERE card_id IS :cardId")
    suspend fun deleteCard(cardId: String)

}