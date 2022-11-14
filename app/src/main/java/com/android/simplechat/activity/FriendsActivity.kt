package com.android.simplechat.activity

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.simplechat.adapter.FriendsListAdapter
import com.android.simplechat.model.User
import com.android.simplechat.view.friend.FriendsView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FriendsActivity : AppCompatActivity() {
    lateinit var friendsView: FriendsView
    private var database: FirebaseDatabase? = null
    var users: ArrayList<User>? = null
    var user: User? = null
    var adapter: FriendsListAdapter? = null
    var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        users = ArrayList()
        adapter = FriendsListAdapter(this, users!!)

        friendsView = FriendsView(this@FriendsActivity, adapter!!)
        setContentView(friendsView)

        dialog = ProgressDialog(this@FriendsActivity).apply {
            setMessage("Uploading image...")
            setCancelable(false)
        }

        database = FirebaseDatabase.getInstance("https://simplechat-a6891-default-rtdb.asia-southeast1.firebasedatabase.app")
        database!!.reference.child("users")
            .addValueEventListener(object : ValueEventListener {
                // This method will be called when it's attached to the DatabaseReference.
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snp in snapshot.children) {
                        val user = snp.getValue(User::class.java)
                        if (!user!!.uid.equals(FirebaseAuth.getInstance().uid)) {
                            users!!.add(user)
                        }
                        adapter!!.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    override fun onResume() {
        super.onResume()
        val currentId = FirebaseAuth.getInstance().uid
        database!!.reference.child("presence")
            .child(currentId!!)
            .setValue("online")
    }

    override fun onPause() {
        super.onPause()
        val currentId = FirebaseAuth.getInstance().uid
        database!!.reference.child("presence")
            .child(currentId!!)
            .setValue("offline")
    }
}
