package android.example.com.matsusmagic.model

import androidx.room.Dao
import androidx.room.Query

@Dao
interface CardToSearchDao {

    @Query("SELECT * FROM CardToSearch WHERE name is :cardname")
    suspend fun getCardIds(cardname: String) : MutableList<CardToSearch>

    @Query("SELECT name FROM CardToSearch WHERE name LIKE '%' || :cardname || '%'")
    suspend fun getCardNames(cardname: String) : MutableList<String>

    @Query("SELECT * FROM CardToSearch WHERE name LIKE '%' || :cardname || '%'")
    suspend fun getCardIdsFuzzy(cardname: String) : MutableList<CardToSearch>
}