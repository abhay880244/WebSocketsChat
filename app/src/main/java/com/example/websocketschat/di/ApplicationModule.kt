package com.example.websocketschat.di

import android.app.Application
import android.content.Context
import com.example.ChatDatabase
import com.example.GsonMessageAdapter
import com.example.websocketschat.SocketService
import com.example.websocketschat.api.ApiHelper
import com.example.websocketschat.api.ApiService
import com.example.websocketschat.utils.Constants
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.retry.ExponentialWithJitterBackoffStrategy
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
open class ApplicationModule(val application: Application) {


    @Provides
    fun getContext(): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun apiHelper(apiService: ApiService) : ApiHelper = ApiHelper(apiService)

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    open fun chatDatabase() : ChatDatabase = ChatDatabase.getDatabase(application.applicationContext)


    val lifecycle = AndroidLifecycle.ofApplicationForeground(application)
    val backoffStrategy = ExponentialWithJitterBackoffStrategy(5000, 5000)


    val SOCKET_BASE_URL = "ws://192.168.0.100:8000/ws/chat/GROUP1/"

    @Singleton
    @Provides
    open fun socketService(okHttpClient: OkHttpClient): SocketService = Scarlet.Builder()
        .webSocketFactory(okHttpClient.newWebSocketFactory(SOCKET_BASE_URL))
        .addMessageAdapterFactory(GsonMessageAdapter.Factory())
        .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
        .backoffStrategy(backoffStrategy)
        .lifecycle(lifecycle)
        .build()
        .create()
}