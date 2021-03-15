package android.example.com.matsusmagic.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CardToSearch::class, Card::class, Decks::class, CardsInDecks::class], version = 2)
@TypeConverters(TypeConverter::class)
abstract class CardDatabase: RoomDatabase() {
    abstract fun cardDao(): CardDao

    companion object {
        @Volatile private var instance: CardDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CardDatabase::class.java,
            "carddatabase"
        ).createFromAsset("CardDataBase.db")

        .build()
    }
}