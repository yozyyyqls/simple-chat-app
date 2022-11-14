package com.android.simplechat.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.simplechat.model.Message
import com.android.simplechat.view.chat.MessageView
import com.android.simplechat.view.chat.ReceiveMessageView
import com.android.simplechat.view.chat.SendMessageView
import com.google.firebase.auth.FirebaseAuth

class ChatMessageListAdapter constructor(
    val context: Context,
    messages: ArrayList<Message>?,
    senderRoom: String,
    receiverRoom: String
) : RecyclerView.Adapter<ChatMessageListViewHolder?>() {

    private var messages: ArrayList<Message>
    private val ITEM_SEND = 1
    private val ITEM_RECEIVE = 2
    private val senderRoom: String
    private val receiverRoom: String

    init {
        this.messages = messages!!
        this.senderRoom = senderRoom
        this.receiverRoom = receiverRoom
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessageListViewHolder {
        if (viewType == ITEM_SEND) {
            val view = SendMessageView(context) as MessageView
            return ChatMessageListViewHolder(view)
        }
        val view = ReceiveMessageView(context) as MessageView
        return ChatMessageListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatMessageListViewHolder, position: Int) {
        val message: Message = messages[position]
        if (holder.itemView is SendMessageView) {
            val view = holder.itemView as SendMessageView
            view.message = message.message ?: ""
        } else {
            val view = holder.itemView as ReceiveMessageView
            view.message = message.message ?: ""
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.senderId == FirebaseAuth.getInstance().uid) {
            ITEM_SEND
        } else {
            ITEM_RECEIVE
        }
    }
}
