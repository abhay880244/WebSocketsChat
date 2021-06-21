package com.example.websocketschat

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ChatDatabase
import com.example.websocketschat.db.repository.ChatRepository
import com.example.websocketschat.model.MessageData
import com.example.websocketschat.ui.viewmodel.ChatViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*


@RunWith(AndroidJUnit4::class)
class ChatViewModelTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var chatViewModel: ChatViewModel

    lateinit var chatDB: ChatDatabase

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        chatDB = Room.inMemoryDatabaseBuilder(
                context, ChatDatabase::class.java
        ).allowMainThreadQueries().build()

        chatViewModel = ChatViewModel(ChatRepository(chatDB))
    }

    @Test
    fun testIfMessageIsInserted() {

        val message = MessageData("fgh", Calendar.getInstance().time, 7657, 54)

        chatViewModel.insertMessage(message);

        val list: List<MessageData> = chatViewModel.getChatByRoomId(message.room_id).getOrAwaitValue()

        val isInserted = (list as MutableList<MessageData>).firstOrNull { it.message == message.message } != null

        assert(isInserted)

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        chatDB.close()
    }
}