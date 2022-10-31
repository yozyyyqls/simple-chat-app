package com.android.simplechat.adapter

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.simplechat.R
import com.android.simplechat.model.User
import com.android.simplechat.view.FriendCardView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FriendsListAdapter constructor(
    var context: Context,
    var userList: ArrayList<User>
) : RecyclerView.Adapter<FriendsListViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsListViewHolder {
        var v = FriendCardView(context)
        return FriendsListViewHolder(v)
    }

    override fun onBindViewHolder(holder: FriendsListViewHolder, position: Int) {
        if (userList.size == 0) return
        val user = userList[position]
        val view = holder.itemView as FriendCardView

        val imageRef: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(user.profileImage!!)

        Glide.with(context)
            .load(imageRef)
            .placeholder(context.getDrawable(R.drawable.pic_profile))
            .into(view.profile!!)
        view.name = user.name!!

        Log.d(TAG, "User Name: ${user.name}, User Uid: ${user.uid}, Phone: ${user.phoneNumber}, Profile URL: ${user.profileImage}")
    }

    override fun getItemCount(): Int = userList.size

    companion object {
        const val TAG = "FriendsListAdapter"
    }
}
