package com.rootsquare.promote

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rootsquare.promote.TestingOtp.OtpActivity
import com.rootsquare.promote.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private var EMAILKEY: String = "EMAILKEY"
    private var PASSWORDKEY: String = "PASSWORDKEY"
    private var PHONEKEY: String = "PHONEKEY"
    private var NAMEKEY: String = "NAMEKEY"
    val db = Firebase.firestore

    // this stores the phone number of the user
    var number: String = ""

    // we will use this to match the sent otp from firebase
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Initialize Firebase Auth
        auth = Firebase.auth

//        val spinner = findViewById<ProgressBar>(R.id.progressBar)
//        spinner.visibility = View.GONE


        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            // This method is called when the verification is completed
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
                Log.d("GFG", "onVerificationCompleted Success")
            }

            // Called when verification is failed add log statement to see the exception
            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("GFG", "onVerificationFailed $e")
            }

            // On code is sent by the firebase this method is called
            // in here we start a new activity where user can enter the OTP
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("GFG", "onCodeSent: $verificationId")
                storedVerificationId = verificationId
                resendToken = token

                // Start a new activity using intent
                // also send the storedVerificationId using intent
                // we will use this id to send the otp back to firebase
                val intent = Intent(applicationContext, OtpActivity::class.java)
                intent.putExtra("storedVerificationId", storedVerificationId)
                startActivity(intent)
                finish()
            }
        }

        binding.btnRegister.setOnClickListener {
            //    val email = binding.etRegEmail.text.toString()
            val phone = binding.etRegPhone.text.toString()
            //    val name = binding.etName.text.toString()
            //    val password = binding.etRegPassword.text.toString()
            //    Log.d("data = ", email)
            if (binding.etRegPhone.text.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                //    spinner.visibility = View.VISIBLE
                //  spinner.isClickable = false
                save(phone)
            }
        }
        setContentView(binding.root)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        Toast.makeText(this, "heloooo", Toast.LENGTH_SHORT).show()
        if (currentUser != null) {
            val intent = Intent(this@MainActivity, AddLinksScreen::class.java)
            startActivity(intent)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")

                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }


    private fun save(phone: String) {
        val map = mutableMapOf<String, String>()

        map.put(PHONEKEY, phone)


        val dbCollectionFile = db.collection("collectiondemo")
        val results = dbCollectionFile.whereEqualTo(PHONEKEY, phone)
        //  val emailCheck = dbCollectionFile.whereEqualTo(EMAILKEY, email)

        var phoneExists = false
        //   var emailExists = false

        results.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result) {
                    if (document.exists()) {
                        phoneExists = true
                        val phoneInfo = document.getString(PHONEKEY)
                        Log.d("already = ", "phone already exists $phoneInfo")
                        Toast.makeText(
                            this,
                            "You are our existing user verify with OTP",
                            Toast.LENGTH_SHORT
                        ).show()
                        sendNumberForOtp()
                        break // Exit the loop because the phone number exists
                    }
                }
            } else {
                Log.d("TAG", "Error getting documents: ", task.exception)
            }
            // Check if both phone number and email do not exist, then register the user
            if (!phoneExists) {
                Log.d("not exists = ", "phone and email do not exist")
                //   registerCallFirebase(email, password)
                sendNumberForOtp()
                db.collection("collectiondemo").add(map)
                    .addOnSuccessListener {
                        Toast.makeText(
                            this,
                            "User details submitted successfully",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "issues", Toast.LENGTH_LONG).show()
                        Log.d("err---: ", it.toString())
                    }
            }
        }

        /*   emailCheck.get().addOnCompleteListener { task ->
               if (task.isSuccessful) {
                   for (document in task.result) {
                       if (document.exists()) {
                           emailExists = true
                           val emailInfo = document.getString(EMAILKEY)
                           Log.d("already = ", "email already exists $emailInfo")
                           Toast.makeText(
                               this,
                               "Email already exists",
                               Toast.LENGTH_SHORT
                           ).show()
                           break // Exit the loop because the email exists
                       }
                   }
               } else {
                   Log.d("TAG", "Error getting documents: ", task.exception)
               }

               // Check if both phone number and email do not exist, then register the user
               if (!phoneExists && !emailExists) {
                   Log.d("not exists = ", "phone and email do not exist")
                   registerCallFirebase(email, password)
                   db.collection("collectiondemo").add(map)
                       .addOnSuccessListener {
                           Toast.makeText(
                               this,
                               "User details submitted successfully",
                               Toast.LENGTH_LONG
                           ).show()

                       }
                       .addOnFailureListener {
                           Toast.makeText(this, "issues", Toast.LENGTH_LONG).show()
                           Log.d("err---: ", it.toString())
                       }
               }
           }*/

    }

    private fun registerCallFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("success case = ", "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(
                        baseContext, "Successfully Register",
                        Toast.LENGTH_SHORT
                    ).show()
                    //      updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("failed case = ", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, task.exception?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    //     updateUI(null)
                }
            }

    }


    private fun sendNumberForOtp() {
        number = findViewById<EditText>(R.id.et_reg_phone).text.trim().toString()

        // get the phone number from edit text and append the country cde with it
        if (number.isNotEmpty()) {
            number = "+91$number"
            sendVerificationCode(number)
        } else {
            Toast.makeText(this, "Enter mobile number", Toast.LENGTH_SHORT).show()
        }
    }

    // this method sends the verification code
    // and starts the callback of verification
    // which is implemented above in onCreate
    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        Log.d("GFG", "Auth started")
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

}