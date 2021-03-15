package android.example.com.matsusmagic.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CardsApiService {
    private val BASE_URL = "https://api.scryfall.com/"

    private val api = Retrofit.Builder()
        .baseUrl((BASE_URL))
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CardsApi::class.java)

    fun getCard(Id: String): Single<Card> {
        return api.getCard(Id)
    }
    fun getRulings(Id: String): Single<Rulings> {
        return api.getRulings(Id)
    }
    fun getCardOfTheDay(): Single<Card> {
        return api.getCardOfTheDay()
    }
}