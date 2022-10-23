package com.android.simplechat.activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.simplechat.model.User
import com.android.simplechat.view.SetupProfileView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.collections.HashMap

class SetupProfileActivity : AppCompatActivity() {
    private lateinit var setupProfileView: SetupProfileView

    var auth: FirebaseAuth? = null
    var database: FirebaseDatabase? = null
    var storage: FirebaseStorage? = null
    var selectedImage: Uri? = null
    var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupProfileView = SetupProfileView(this@SetupProfileActivity)
        setContentView(setupProfileView)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://simplechat-a6891-default-rtdb.asia-southeast1.firebasedatabase.app")
        storage = FirebaseStorage.getInstance()

        dialog = ProgressDialog(this@SetupProfileActivity).apply {
            setMessage("Updating Profile...")
            setCancelable(false)
        }

        setupProfileView.circleImageView.setOnClickListener {
            // select a photo from gallery.
            val intent = Intent().apply {
                action = Intent.ACTION_GET_CONTENT
                type = "image/*"
            }
            startActivityForResult(intent, 45)
        }

        setupProfileView.btContinue.setOnClickListener {
            val name: String = setupProfileView.etNameInput.text.toString()
            if (name.isBlank()) {
                setupProfileView.etNameInput.setError("Please type a user name")
            }
            dialog!!.show()

            if (selectedImage != null) {
                val reference = storage!!.reference
                    .child("profile")
                    .child(auth!!.uid!!) // current path: /profile/<uid>/
                // Put user's profile image to the cloud storage.
                reference.putFile(selectedImage!!).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // After uploading a file, can get a Url to download the file.
                        reference.downloadUrl.addOnCompleteListener { uri ->
                            // Image Url in cloud storage.
                            val imageUrl = uri.toString()
                            val uid = auth!!.uid
                            val name: String = setupProfileView.etNameInput.text.toString()
                            val phone = auth!!.currentUser!!.phoneNumber
                            val user = User(uid, name, phone, imageUrl)

                            // Put user's information to the database.
                            database!!.reference
                                .child("users")
                                .child(uid!!) // current path: /users/<uid>/
                                .setValue(user)
                                .addOnCompleteListener {
                                    Log.d(TAG, "save user data in database successfully")
                                    dialog!!.dismiss()
                                    val intent = Intent(this@SetupProfileActivity, FriendsActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                        }
                    }
                }
            }
        }
    }

    /**
     * Get result data (the Uri of the image) from gallery
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && data.data != null) {
            // File path
            val imageUrl = data.data
            val storage = FirebaseStorage.getInstance()
            val time = Date().time

            val reference = storage.reference
                .child("profile")
                .child(time.toString() + "")
            reference.putFile(imageUrl!!).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    reference.downloadUrl.addOnCompleteListener { uri ->
                        val filePath = uri.toString()
                        val obj = HashMap<String, Any>()
                        obj["image"] = filePath
                        database!!.reference
                            .child("users")
                            .child(FirebaseAuth.getInstance().uid!!)
                            .updateChildren(obj).addOnCompleteListener { }
                    }
                }
            }
            setupProfileView.circleImageView.setImageURI(imageUrl)
            this.selectedImage = imageUrl
        }
    }

    companion object {
        final val TAG = "SetupProfileActivity"
    }
}
