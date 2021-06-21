package com.example.websocketschat.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Chat")
class MessageData(var message: String, var sent_at: Date, var from_user_id: Int, var room_id: Int) {
    @PrimaryKey
    var id: Int = 0
}