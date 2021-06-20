package com.example.websocketschat

import com.example.websocketschat.model.MessageData
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable

interface SocketService {
    @Send
    fun sendMessage(action: MessageData)

    @Receive
    fun observeMessage(): Flowable<MessageData>

    @Receive
    fun observeWebSocketEvent(): Flowable<WebSocket.Event>
}