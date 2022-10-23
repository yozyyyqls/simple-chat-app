package com.android.simplechat.view

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.simplechat.adapter.FriendsListAdapter

class FriendsListView constructor(
    context: Context,
    private val adapter: FriendsListAdapter
) : RecyclerView(context) {
    init {
        layoutManager = GridLayoutManager(context, 2)
        setAdapter(adapter)
    }
}
