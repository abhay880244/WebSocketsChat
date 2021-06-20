package com.example.websocketschat

import android.app.Application
import com.example.GsonMessageAdapter
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.retry.ExponentialWithJitterBackoffStrategy
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class ChatApplication : Application() {
    companion object {
        val SOCKET_BASE_URL = "ws://192.168.0.100:8000/ws/chat/GROUP1/"
        lateinit var socket: SocketService

        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()

        application = this
        setUpScarletClient()
    }

    private fun setUpScarletClient() {

        val lifecycle = AndroidLifecycle.ofApplicationForeground(this)
        val backoffStrategy = ExponentialWithJitterBackoffStrategy(5000, 5000)

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        socket = Scarlet.Builder()
            .webSocketFactory(okHttpClient.newWebSocketFactory(SOCKET_BASE_URL))
            .addMessageAdapterFactory(GsonMessageAdapter.Factory())
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .backoffStrategy(backoffStrategy)
            .lifecycle(lifecycle)
            .build()
            .create()
    }
}