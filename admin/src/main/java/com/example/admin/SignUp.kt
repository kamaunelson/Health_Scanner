package com.example.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Auth and Realtime Database
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()

        val loginButton = findViewById<Button>(R.id.loginButton)
        val signupButton = findViewById<Button>(R.id.signButton)

        loginButton.setOnClickListener {
            // TODO: Implement login logic
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        signupButton.setOnClickListener {
            performSignup()
        }
    }

    private fun performSignup() {

        val name = findViewById<EditText>(R.id.fullnameEditText)
        val email = findViewById<EditText>(R.id.usernameEditText)
        val phoneno = findViewById<EditText>(R.id.phoneEditText)
        val password = findViewById<EditText>(R.id.passwordEditText)
        val age = findViewById<EditText>(R.id.ageEditText)

        val inname = name.text.toString()
        val inemail = email.text.toString()
        val inphoneno = phoneno.text.toString()
        val inpassword = password.text.toString()
        val inage = age.text.toString()

        if (inemail.isEmpty() || inpassword.isEmpty()) {
            Toast.makeText(this, "Kindly fill all the spaces provided", Toast.LENGTH_SHORT).show()
            return
        }

        // Create a user with email and password
        auth.createUserWithEmailAndPassword(inemail, inpassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration successful, now update the user profile with additional data
                    val user = auth.currentUser

                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(inname)
                        .build()

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                // User profile updated successfully
                                Toast.makeText(
                                    baseContext,
                                    "Successful registration.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Store additional user data in Firebase Realtime Database
                                val userData = User(inname, inphoneno, inage)
                                storeUserData(user.uid, userData)

                                // Redirect to the login screen
                                val intent = Intent(this, Login::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    baseContext,
                                    "Failed to update user profile.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    // Registration failed, display an error message
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener(this) {
                // Handle other registration failures
                Toast.makeText(this, "Error occurred. ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }

    // Function to store additional user data in Firebase Realtime Database
    private fun storeUserData(userId: String, userData: User) {
        val databaseReference = database.reference.child("users").child(userId)
        databaseReference.setValue(userData)
    }
}
