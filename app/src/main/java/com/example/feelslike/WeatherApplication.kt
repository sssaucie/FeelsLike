package com.example.feelslike

import android.app.Application
import com.example.feelslike.model.database.FeelsLikeDatabase
import com.example.feelslike.model.network.*
import com.example.feelslike.model.repository.WeatherRepository
import com.example.feelslike.model.repository.WeatherRepositoryImpl
import kotlinx.coroutines.DelicateCoroutinesApi
import org.kodein.di.*
import org.kodein.di.android.x.androidXModule

class WeatherApplication : Application(), DIAware {
    @DelicateCoroutinesApi
    override val di = DI.lazy {
        import(androidXModule(this@WeatherApplication))

//        bind() from singleton { FeelsLikeDatabase(instance()) }
        bind() from singleton { instance<FeelsLikeDatabase>().weatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<WeatherRepository>() with singleton { WeatherRepositoryImpl(instance(), instance()) }
    }
}