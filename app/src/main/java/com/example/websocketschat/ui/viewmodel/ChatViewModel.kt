package com.example.websocketschat.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.websocketschat.db.repository.ChatRepository
import com.example.websocketschat.model.MessageData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class ChatViewModel @Inject constructor(var repository: ChatRepository) : ViewModel() {

    fun sendMessage(messageData: MessageData)
    = viewModelScope.launch(Dispatchers.IO) {
        repository.sendMessageToServer(messageData)
    }


    fun getChatByRoomId(roomId: Int): LiveData<List<MessageData>> {
        return repository.getChatByRoomId(roomId)
    }

    fun insertMessage(messageData: MessageData) = viewModelScope.launch{
        repository.insertMessage(messageData)
    }
}