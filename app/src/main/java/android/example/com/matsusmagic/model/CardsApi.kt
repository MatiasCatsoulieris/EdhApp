package android.example.com.matsusmagic.model
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path



interface CardsApi {
    @GET("cards/{id}")
    fun getCard(@Path ("id") scryfallId: String = "austere command"): Single<Card>
    @GET("cards/{id}/rulings")
    fun getRulings(@Path ("id") scryfallId: String = "austere command"): Single<Rulings>
    @GET("cards/random?q=is%3Acommander")
    fun getCardOfTheDay(): Single<Card>

}