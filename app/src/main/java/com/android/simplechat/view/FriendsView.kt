package com.android.simplechat.view

import android.content.Context
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import com.android.simplechat.R
import com.android.simplechat.adapter.FriendsListAdapter

class FriendsView constructor(
    context: Context,
    adapter: FriendsListAdapter
) : LinearLayout(context) {

    private var tvFriendsTitle: TextView? = null
    private var tvFriendsContent: TextView? = null
    private var llFriendsContainer: LinearLayout? = null
    private var rvFriendsList: FriendsListView? = null

    init {
        this.orientation = VERTICAL
        this.setBackgroundColor(context.getColor(R.color.colorSecondary))

        // Title
        tvFriendsTitle = TextView(context).apply {
            text = context.getString(R.string.text_friends_title)
            setTextAppearance(R.style.SimpleChatTitleText)
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        }
        this.addView(tvFriendsTitle)

        // Content
        tvFriendsContent = TextView(context).apply {
            text = context.getString(R.string.text_friends_content)
            setTextAppearance(R.style.SimpleChatContentText)
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        }
        this.addView(tvFriendsContent)

        // Friends List RecycleView
        rvFriendsList = FriendsListView(context, adapter).apply {
            layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
        }

        // Friends List Linearlayout Container
        llFriendsContainer = LinearLayout(context).apply {
            orientation = VERTICAL
            layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
            addView(rvFriendsList)
        }
        this.addView(llFriendsContainer)
    }
}
