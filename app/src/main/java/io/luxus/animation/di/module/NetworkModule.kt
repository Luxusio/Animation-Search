package io.luxus.animation.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.luxus.animation.data.source.remote.RetrofitService
import io.luxus.animation.util.Constants.BASE_URL
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofitService(
        gsonConverterFactory: GsonConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        okHttpClient: OkHttpClient
    ): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .client(okHttpClient)
            .build()
            .create(RetrofitService::class.java)
    }

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        //val interceptor = HttpLoggingInterceptor()
        //interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            //.cache(cache)
            //.addNetworkInterceptor(interceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("laftel", "TeJava")
                    .build()

                chain.proceed(request)
            }
            .build()
    }

//    @Provides
//    fun providesCache(context: Context): Cache {
//        val cacheSize = (5 * 1024 * 1024).toLong()
//        return Cache(context.cacheDir, cacheSize)
//    }

    @Provides
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

}