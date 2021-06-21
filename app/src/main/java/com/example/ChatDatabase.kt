package com.example

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.websocketschat.db.DataConverter
import com.example.websocketschat.db.dao.ChatDao
import com.example.websocketschat.model.MessageData

@Database(
    entities = [com.example.websocketschat.model.Room::class, MessageData::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun chatDao(): ChatDao

    companion object {

        @Volatile
        private var INSTANCE: ChatDatabase? = null

        fun getDatabase(context: Context): ChatDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChatDatabase::class.java,
                    "chat_database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}