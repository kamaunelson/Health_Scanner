package com.example.admin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateAccount : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_account)

        auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().reference.child("users").child(userId.orEmpty())

        val updatedNameEditText = findViewById<EditText>(R.id.updatedNameEditText)
        val updatedPhoneEditText = findViewById<EditText>(R.id.updatedPhoneEditText)
        val updatedAgeEditText = findViewById<EditText>(R.id.updatedAgeEditText)
        val updateButton = findViewById<Button>(R.id.updateButton)
        val deleteButton = findViewById<Button>(R.id.deleteButton)

        // Retrieve the current user data and display it in the EditText fields
        databaseReference.get().addOnSuccessListener { snapshot ->
            val user = snapshot.getValue(User::class.java)
            user?.let {
                updatedNameEditText.setText(it.inname)
                updatedPhoneEditText.setText(it.inphoneno)
                updatedAgeEditText.setText(it.inage)
            }

            deleteButton.setOnClickListener {
                // Prompt the user to confirm account deletion
                disableAccount()
            }
        }

        updateButton.setOnClickListener {
            val updatedName = updatedNameEditText.text.toString()
            val updatedPhone = updatedPhoneEditText.text.toString()
            val updatedAge = updatedAgeEditText.text.toString()

            // Update the user's information in the Firebase Realtime Database
            val updatedUserData = User(updatedName, updatedPhone, updatedAge)
            databaseReference.setValue(updatedUserData)
                .addOnSuccessListener {
                    // Data successfully updated in the database
                    // Update the user's display name in Firebase Authentication
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(updatedName)
                        .build()
                    user?.updateProfile(profileUpdates)

                    // Provide user feedback
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    finish() // Close the activity after the update
                }
                .addOnFailureListener {
                    // Handle the error
                    Toast.makeText(this, "Failed to update profile: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
    // Disable the user's account in the Firebase Realtime Database
    private fun disableAccount() {
        val userId = auth.currentUser?.uid
        val databaseReference = FirebaseDatabase.getInstance().reference.child("users").child(userId.orEmpty())

        // Update the account status to "disabled"
        databaseReference.child("accountStatus").setValue("disabled")
            .addOnSuccessListener {
                // Account status updated successfully
                // You may want to log the user out or perform other actions
            }
            .addOnFailureListener {
                // Handle the error
            }
    }

}