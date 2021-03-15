package android.example.com.matsusmagic.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Card (
    @SerializedName ("id")
    val card_id: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("type_line")
    val type_line: String?,
    @SerializedName("mana_cost")
    val mana_cost: String?,
    @SerializedName("set")
    val set: String?,
    @SerializedName("set_name")
    val set_name: String?,
    @SerializedName("oracle_text")
    val oracle_text: String?,
    @SerializedName("purchase_uris")
    val purchase_uris: Map<String?, String>?,
    @SerializedName("rulings_uri")
    val rulings_uri: String?,
    @SerializedName("image_uris")
    @Embedded
    val image_uris: ImageUris,
    @SerializedName("legalities")
    val legalities: Map<String?, String>?,
    @SerializedName("prices")
    @Embedded
    val prices: Prices?,
    @SerializedName("related_uris")
    val related_uris: Map<String?, String>?,

) {
    @PrimaryKey(autoGenerate = true)
    var dbId: Int = 0
}


data class Prices (
    @SerializedName("usd")
    val usd: Double?,
    @SerializedName("tix")
    val tix: Double?
)

data class ImageUris (
    @SerializedName ("small")
    val small: String?,
    @SerializedName("large")
    val large: String?
)
data class Rulings (
    @SerializedName("data")
    val rulingData: List<Map<String, String>>
        )

@Entity
data class CardToSearch (
    @PrimaryKey(autoGenerate = true)
    var CardToSearchId: Int = 0,
    val scryfallId: String,
    val name: String?

    )

@Entity
data class Decks (
    @PrimaryKey(autoGenerate = true)
    var deckId: Int = 0,
    var deckName: String,
    var cardQuantity: Int,
    var totalPriceUsd: Double,
    var totalPriceTix: Double,
)
@Entity
data class CardsInDecks (
    @PrimaryKey ( autoGenerate = true)
    var regId: Int = 0,
    var deckId: Int,
    var cardId: String,
    var isCommander: Boolean
        )
