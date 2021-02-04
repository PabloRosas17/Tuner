package interview.android.tuner.core

import android.app.Application
import interview.android.tuner.controller.*
import interview.android.tuner.services.TunesService
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/* @desc core module synchronizes collection of viewmodels and ensures creational factory pattern
    will generate only certain amount of instances that are needed, networking client, and application
    module will synthesize in manifest as the top module for dependency injection framework koin
 * @return an application module to synthesize with manifest */
class MainActivity : Application() {

    /* modules that will inject into the application, factory for networking */
    private val mViewModels = module {
        viewModel { ViewModelComingSoon(get()) }
        viewModel { ViewModelHotTracks(get()) }
        viewModel { ViewModelNewReleases(get()) }
        viewModel { ViewModelTopAlbums(get()) }
        viewModel { ViewModelTopSongs(get()) }
        viewModel { ViewModelFocused(get()) }
        factory { OkHttpClient.Builder().build() }
        factory {
            Retrofit.Builder()
                .client(get<OkHttpClient>())
                .baseUrl(APPLE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
            .build() }
        factory { get<Retrofit>().create(TunesService::class.java) }
    }

    /* list of of modules that will load through koin */
    private val mModules = listOf(mViewModels)

    /* application dependent method used to instantiate an instance of this and link the modules koin will use*/
    override fun onCreate() {
        super.onCreate()
        startKoin {
            /* @androidLogger initiate logger for Koin*/
            /* @androidContext reference for caller context */
            /* @modules reference for modules available */
            androidLogger()
            androidContext(this@MainActivity)
            loadKoinModules(mModules)
        }
    }

    /* base url for retrofit client */
    companion object {
        private const val APPLE_BASE_URL = "https://rss.itunes.apple.com/"
    }
}