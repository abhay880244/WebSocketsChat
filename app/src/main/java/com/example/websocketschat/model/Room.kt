package com.example.websocketschat.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Room")
class Room(var name: String, id: Int) {
    @PrimaryKey
    var id: Int = 0
}