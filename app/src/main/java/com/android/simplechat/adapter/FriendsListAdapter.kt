package com.android.simplechat.adapter

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.simplechat.model.User
import com.android.simplechat.view.FriendCardView
import com.bumptech.glide.Glide

class FriendsListAdapter constructor(
    var context: Context,
    var userList: ArrayList<User>
) : RecyclerView.Adapter<FriendsListViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsListViewHolder {
        var v = FriendCardView(context)
        return FriendsListViewHolder(v)
    }

    override fun onBindViewHolder(holder: FriendsListViewHolder, position: Int) {
        if (userList == null) return
        val user = userList[position]
        val view = holder.itemView as FriendCardView

        view.profile = Glide.with(context).load(user.profileImage).placeholderDrawable
        view.name = user.name!!

        Log.d(TAG, "User Name: ${user.name}, User Uid: ${user.uid}, Phone: ${user.phoneNumber}")
    }

    override fun getItemCount(): Int = userList.size

    companion object {
        final const val TAG = "FriendsListAdapter"
    }
}
