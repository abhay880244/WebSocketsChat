package com.example.websocketschat.db.repository

import androidx.lifecycle.LiveData
import com.example.ChatDatabase
import com.example.websocketschat.SocketService
import com.example.websocketschat.db.dao.ChatDao
import com.example.websocketschat.model.MessageData
import javax.inject.Inject

class ChatRepository @Inject constructor(var database: ChatDatabase, var socket: SocketService? = null) {

    private var chatDao: ChatDao

    init {
        chatDao = database.chatDao()
    }

    fun sendMessageToServer(messageData : MessageData) {
        socket?.sendMessage(messageData)
    }

    suspend fun insertMessage(messageData : MessageData){
        chatDao.insertMessage(messageData)
    }

    fun getChatByRoomId(roomId : Int): LiveData<List<MessageData>> {
        return chatDao.getChatByRoomId(roomId)
    }
}