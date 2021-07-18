package android.example.com.matsusmagic.di

import android.app.Application
import android.example.com.matsusmagic.model.*
import android.example.com.matsusmagic.repositories.MainRepo
import android.example.com.matsusmagic.repositories.MainRepoImpl
import android.example.com.matsusmagic.util.BASE_URL
import android.example.com.matsusmagic.util.YT_BASE_URL
import android.example.com.matsusmagic.viewmodel.*
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()

fun provideYTRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()

fun provideYTApiService(retrofit: Retrofit): YouTubeApi = retrofit.create(YouTubeApi::class.java)
fun provideApiService(retrofit: Retrofit): CardsApi = retrofit.create(CardsApi::class.java)

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), CardDatabase::class.java, "carddatabase")
            .createFromAsset("CardDataBase.db")
            .build()
    }

    single<CardDao> {
        val db: CardDatabase = get()
        db.cardDao()
    }
    single<DecksDao> {
        val db: CardDatabase = get()
        db.deckDao()
    }
    single<CardsInDecksDao> {
        val db: CardDatabase = get()
        db.cardsInDecksDao()
    }
    single<CardToSearchDao> {
        val db: CardDatabase = get()
        db.cardToSearchDao()
    }
}

val networkModule = module {
    single(named("ScryfallApi")) { provideRetrofit(BASE_URL) }
    single { provideApiService(get(named("ScryfallApi"))) }
    single(named("YoutubeApi")) { provideYTRetrofit(YT_BASE_URL) }
    single { provideYTApiService(get(named("YoutubeApi"))) }
}

val repoModule = module {
    single<MainRepo> { MainRepoImpl(get(), get(), get(), get(), get(), get()) }
}

val viewModelModule = module {
    viewModel { CardViewModel(get(), androidApplication()) }
    viewModel { MenuViewModel(get(), androidApplication()) }
    viewModel { SearchViewModel(get(), androidApplication()) }
    viewModel { CommunityContentViewModel(get(), androidApplication()) }
    viewModel { DeckListViewModel(get(), androidApplication()) }
    viewModel { DeckViewModel(get(), androidApplication()) }

}