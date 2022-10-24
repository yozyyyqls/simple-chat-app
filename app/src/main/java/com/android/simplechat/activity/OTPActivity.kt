package com.android.simplechat.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.simplechat.view.OtpPageView
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {
    private lateinit var otpPageView: OtpPageView
    var verificationId: String? = null
    var auth: FirebaseAuth? = null
    var database: FirebaseDatabase? = null
    var otpValue: String? = null
    private var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val phoneNumber: String? = intent.getStringExtra("PHONE_NUMBER")

        otpPageView = OtpPageView(this, phoneNumber!!)
        setContentView(otpPageView)

        dialog = ProgressDialog(this@OTPActivity).apply {
            setMessage("Sending OTP...")
            setCancelable(false)
            show()
        }

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:$credential")
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                Log.w(TAG, "onVerificationFailed", exception)
            }

            override fun onCodeSent(verifyId: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verifyId, forceResendingToken)
                dialog!!.dismiss()
                verificationId = verifyId
                otpPageView.otpView.requestFocus()
                Log.d(TAG, "onCodeSent:$verifyId")
            }
        }

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://simplechat-a6891-default-rtdb.asia-southeast1.firebasedatabase.app")

        val option = PhoneAuthOptions.newBuilder(auth!!)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(option)

        otpPageView.btContinue.setOnClickListener {
            Log.d(TAG, "otp value: $otpValue")
            val credential = PhoneAuthProvider.getCredential(verificationId!!, otpValue!!)
            auth!!.signInWithCredential(credential)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        database!!.reference
                            .child("users")
                            .child(auth!!.uid.toString())
                            .get().addOnCompleteListener {
                                // If uid has existed in database, skip setup profile.
                                val intent = Intent(this@OTPActivity, FriendsActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener {
                                val intent = Intent(this@OTPActivity, SetupProfileActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                    } else {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    companion object {
        const val TAG = "OTPActivity"
    }
}
