package com.example.healthscanner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MyProfile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        val updateButton = findViewById<Button>(R.id.updateButton)

        auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().reference.child("users").child(userId.orEmpty())

        val biodataview = findViewById<TextView>(R.id.biodataContent)
        val usernameview = findViewById<TextView>(R.id.usernameTextView)
        val nameview = findViewById<TextView>(R.id.nameTextView)

        // Retrieve the current user data and display it in the EditText fields
        databaseReference.get().addOnSuccessListener { snapshot ->
            val user = snapshot.getValue(User::class.java)
            user?.let {
                nameview.setText(it.inname)
                usernameview.setText(it.inphoneno)
                biodataview.setText(it.inage)
            }
        }

        updateButton.setOnClickListener {
            // TODO: Implement login logic
            val intent = Intent(this, UpdateAccount::class.java)
            startActivity(intent)
        }

    }
}