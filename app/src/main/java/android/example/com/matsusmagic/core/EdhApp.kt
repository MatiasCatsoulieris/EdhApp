package android.example.com.matsusmagic.core

import android.app.Application
import android.example.com.matsusmagic.di.databaseModule
import android.example.com.matsusmagic.di.networkModule
import android.example.com.matsusmagic.di.repoModule
import android.example.com.matsusmagic.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EdhApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@EdhApp)
            modules(listOf(databaseModule, networkModule, repoModule, viewModelModule))
        }

    }

}