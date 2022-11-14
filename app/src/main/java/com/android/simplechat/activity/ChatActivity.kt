package com.android.simplechat.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.simplechat.adapter.ChatMessageListAdapter
import com.android.simplechat.model.Message
import com.android.simplechat.model.User
import com.android.simplechat.view.chat.ChatView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.util.Date

class ChatActivity : AppCompatActivity() {
    private var chatView: ChatView? = null
    private var adapter: ChatMessageListAdapter? = null
    private var messages: ArrayList<Message>? = null
    private var senderRoom: String? = null
    private var receiverRoom: String? = null
    private var database: FirebaseDatabase? = null
    private var storage: FirebaseStorage? = null
    private var senderUid: String? = null
    private var receiverUid: String? = null
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user: User = intent.getSerializableExtra("USER") as User
        database = FirebaseDatabase.getInstance("https://simplechat-a6891-default-rtdb.asia-southeast1.firebasedatabase.app")
        storage = FirebaseStorage.getInstance()
        senderUid = FirebaseAuth.getInstance().uid
        receiverUid = user.uid
        senderRoom = senderUid + receiverUid
        receiverRoom = receiverUid + senderUid
        messages = ArrayList()

        adapter = ChatMessageListAdapter(this@ChatActivity, messages, senderRoom!!, receiverRoom!!)
        chatView = ChatView(this@ChatActivity, user, adapter!!)
        setContentView(chatView)

        // Load chatting message history.
        database!!.reference
            .child("chats")
            .child(senderRoom!!)
            .child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messages!!.clear()
                    for (snap in snapshot.children) {
                        val message: Message? = snap.getValue(Message::class.java)
                        message!!.messageId = snap.key
                        messages!!.add(message)
                    }
                    adapter!!.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {}
            })

        // Send message and store message in database.
        chatView!!.apply {
            sendMessage!!.setOnClickListener {
                val msgText = chatView!!.messageText
                val date = Date()
                val message = Message(msgText, senderUid, date.time)
                chatView!!.messageText = ""
                val randomKey = database!!.reference.push().key
                val lastMessageObj = HashMap<String, Any>()
                lastMessageObj["lastMessage"] = message.message!!
                lastMessageObj["lastMessageTime"] = date.time

                database!!.reference.child("chats").child(senderRoom!!) // path:/chats/<senderRoom>
                    .updateChildren(lastMessageObj)
                database!!.reference.child("chats").child(receiverRoom!!) // path:/chats/<receiverRoom>
                    .updateChildren(lastMessageObj)
                database!!.reference.child("chats").child(senderRoom!!)
                    .child("messages")
                    .child(randomKey!!) // path:/chats/<senderRoom>/messages/<randomKey>/
                    .setValue(message)
                    .addOnSuccessListener {
                        database!!.reference.child("chats").child(receiverRoom!!)
                            .child("messages")
                            .child(randomKey!!) // path:/chats/<receiverRoom>/messages/<randomKey>/
                            .setValue(message)
                            .addOnSuccessListener {}
                    }
            }
            addPhoto!!.setOnClickListener {
                Toast.makeText(this@ChatActivity, "Coming soon", Toast.LENGTH_SHORT)
                    .show()
            }
            attachFile!!.setOnClickListener {
                Toast.makeText(this@ChatActivity, "Coming soon", Toast.LENGTH_SHORT)
                    .show()
            }
        }

//        val handler = Handler()
//        chatView!!.messageBox?.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//                database!!.reference.child("presence")
//                    .child(senderRoom!!)
//                    .setValue("typing...")
//                var userStoppedTyping = Runnable {
//                    database!!.reference.child("presence")
//                        .child(senderUid!!)
//                        .setValue("online")
//                }
//                handler.apply {
//                    removeCallbacksAndMessages(null)
//                    postDelayed(userStoppedTyping, 1000)
//                }
//            }
//        })
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()
        val currentId = auth.uid!!
        database!!.reference.child("presence")
            .child(currentId)
            .setValue("online")
    }

    override fun onPause() {
        super.onPause()
        val currentId = auth.uid!!
        database!!.reference.child("presence")
            .child(currentId)
            .setValue("offline")
    }
}
