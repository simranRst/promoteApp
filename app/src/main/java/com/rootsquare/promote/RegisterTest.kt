package com.rootsquare.promote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterTest : AppCompatActivity() {

    val db = Firebase.firestore
    lateinit var email: EditText
    lateinit var phone: EditText
    lateinit var password: EditText
    lateinit var button: Button
    private var EMAILKEY:String = "EMAILKEY"
    private var PASSWORDKEY:String = "PASSWORDKEY"
    private var PHONEKEY:String = "PHONEKEY"
    private val registerKey = "registerCollection"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_test)

        email = findViewById(R.id.editTextTextEmailAddress)
        button = findViewById(R.id.button)
        password = findViewById(R.id.editTextTextPassword)
        phone = findViewById(R.id.editTextPhone)

        button.setOnClickListener {
            save()
        }
    }

    fun save(){
        val email1 = email.text.toString()
        val phone1 = phone.text.toString()
        val password1 = password.text.toString()
        val map = mutableMapOf<String , String>()
        map.put(EMAILKEY, email1)
        map.put(PASSWORDKEY, password1)
        map.put(PHONEKEY, phone1)
        db.collection(registerKey).add(map)
            .addOnSuccessListener{
                Toast.makeText(this,"complete listener", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(this,"issues", Toast.LENGTH_LONG).show()
            Log.d("err---: ", it.toString() )
            }
    }
}