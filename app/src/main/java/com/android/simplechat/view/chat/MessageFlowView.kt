package com.android.simplechat.view.chat

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.simplechat.adapter.ChatMessageListAdapter

class MessageFlowView constructor(
    context: Context,
    adapter: ChatMessageListAdapter
) : RecyclerView(context) {
    init {
        layoutManager = LinearLayoutManager(context)
        setAdapter(adapter)
    }
}
