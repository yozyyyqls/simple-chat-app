package com.android.simplechat.view.friend

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.simplechat.adapter.FriendsListAdapter

class FriendsListView constructor(
    context: Context,
    adapter: FriendsListAdapter
) : RecyclerView(context) {
    init {
        layoutManager = LinearLayoutManager(context)
        setAdapter(adapter)
    }
}
