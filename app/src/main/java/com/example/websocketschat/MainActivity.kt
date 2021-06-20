package com.example.websocketschat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.websocketschat.model.MessageData
import com.tinder.scarlet.WebSocket
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MAIN_ACT"
    var disposables: MutableList<Disposable> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeData()
    }

    private fun observeData() {
        // Observe the WebSocket connection
        disposables.plusAssign(ChatApplication.socket.observeWebSocketEvent()
            .subscribe({
                // If connection is open
                if (it is WebSocket.Event.OnConnectionOpened<*>) {

                }
            }, { error ->
                Log.d(TAG, "Error while observing socket ${error.cause}")
            }))

// Observe the ticker channel
        disposables.plusAssign(ChatApplication.socket.observeMessage()
            .subscribe({
                Log.d(TAG, "test ${it.message}")
            }, { error ->
                Log.d(TAG, "Error while observing  ${error.cause}")
            }))
    }

    fun sendMessage(){
        // Initialise parameters
        val messageData = MessageData("khfgxhchjgffg")

        ChatApplication.socket.sendMessage(messageData)
    }
}