package com.markvtls.howdud.data.source.local

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.markvtls.howdud.data.dto.Friend
import com.markvtls.howdud.data.dto.UserChats
import com.markvtls.howdud.domain.model.Message
import kotlinx.coroutines.flow.*
import java.util.*

class FirestoreDatabase {

    private val db = Firebase.firestore
    private val friends = mutableListOf<Friend>()


    suspend fun getChatsList(userId: String): Flow<UserChats?> {
        return db.collection("users")
            .document(userId)
            .snapshots().map { querySnapshot -> querySnapshot.toObject() }
    }

    suspend fun getMessages(chatId: String): Flow<List<Message?>> {
        return db.collection("chats")
            .document(chatId).collection("messages")
            .snapshots().map { querySnapshot -> querySnapshot.toObjects()  }
    }

    fun saveNewUser(userId: String) {

        db.collection("users").document(userId)
            .set(UserChats(), SetOptions.merge())
    }

    fun sendNewMessage(chatId: String, message: Message) {
        db.collection("chats").document(chatId).collection("messages")
            .document(Timestamp.now().toString()).set(message)
    }


    private suspend fun getUsersFriends(contacts: Map<String,String>): Flow<UserChats?> {


        contacts.forEach {
            val number = it.key
            val name = it.value
            db.collection("users")
                .document(number)
                .get()
                .addOnCompleteListener { query ->
                    val document = query.result
                    friends.add(Friend(number,name,document.exists()))
                }
        }

        return db.collection("users")
            .document("userId")
            .snapshots().map { documentSnapshot -> documentSnapshot.takeIf { it.exists() }?.toObject() }


             }

        fun addChatToUserChats(userId: String, chatId: String) {
            db.collection("users").document(userId)
                .update("chats", FieldValue.arrayUnion(chatId))
        }



        fun getSignedFriends(contacts: Map<String,String>): Flow<List<Friend>> = flow {
            getUsersFriends(contacts).collect {
                emit(friends)
            }
        }
    }