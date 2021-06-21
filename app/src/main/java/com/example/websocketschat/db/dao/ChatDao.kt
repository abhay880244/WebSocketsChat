package com.example.websocketschat.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.websocketschat.model.MessageData

@Dao
interface ChatDao {

    @Query("select * from chat where room_id = :roomId order by sent_at asc")
    fun getChatByRoomId(roomId: Int): LiveData<List<MessageData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(messageData: MessageData)

}
