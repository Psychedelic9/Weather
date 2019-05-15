package com.psychedelic.weather.koin

import cn.wj.android.common.net.CacheInterceptor
import cn.wj.android.common.net.InterceptorLogger
import cn.wj.android.common.net.LogInterceptor
import cn.wj.android.common.utils.AppManager
import com.orhanobut.logger.BuildConfig
import com.orhanobut.logger.Logger
import com.psychedelic.weather.adapter.AreaSelectAdapter
import com.psychedelic.weather.adapter.CitySelectAdapter
import com.psychedelic.weather.adapter.DistrictSelectAdapter
import com.psychedelic.weather.adapter.ForecastAdapter
import com.psychedelic.weather.constents.NET_CACHE_SIZE
import com.psychedelic.weather.constents.NET_CONNECT_TIME_OUT
import com.psychedelic.weather.constents.NET_READ_TIME_OUT
import com.psychedelic.weather.constents.NET_WRITE_TIME_OUT
import com.psychedelic.weather.mvvm.CityViewMode
import com.psychedelic.weather.mvvm.DistrictViewMode
import com.psychedelic.weather.mvvm.MainViewModel
import com.psychedelic.weather.mvvm.WeatherViewMode
import com.psychedelic.weather.net.UrlDefinition
import com.psychedelic.weather.net.WebService
import com.psychedelic.weather.repository.DataRepository
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * 网络请求 Module
 */
val netModule: Module = module {

    single<OkHttpClient> {
        //缓存路径
        val file = File(AppManager.getApplication().cacheDir.absolutePath, "HttpCache")
        val cache = Cache(file, NET_CACHE_SIZE)
        OkHttpClient.Builder()
            .connectTimeout(NET_CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(NET_WRITE_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(NET_READ_TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(
                LogInterceptor(
                    if (true) LogInterceptor.Level.BODY else LogInterceptor.Level.NONE,
                    object : InterceptorLogger {
                        override fun log(msg: String) {
                            Logger.d(msg)
                        }
                    }
                )
            )
            .addInterceptor(CacheInterceptor())
            .cache(cache)
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
                .baseUrl(UrlDefinition.BASE_URI)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(get<OkHttpClient>())
                .build()
    }

    single<WebService> {
        get<Retrofit>().create(WebService::class.java)
    }
}

/**
 * 数据仓库 Module
 */
val repositoryModule: Module = module {
    factory { DataRepository() }
}

/**
 * 适配器 Module
 */
val adapterModule: Module = module {
    factory { AreaSelectAdapter() }
    factory { CitySelectAdapter() }
    factory { DistrictSelectAdapter() }
    factory { ForecastAdapter() }
}

/**
 * ViewModel Module
 */
val viewModelModule: Module = module {
    viewModel { MainViewModel(get()) }
    viewModel { CityViewMode(get()) }
    viewModel { DistrictViewMode(get()) }
    viewModel { WeatherViewMode(get()) }


}