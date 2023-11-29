package com.example.healthscanner

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserManagement : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_management)

        // Initialize RecyclerView and other views
        recyclerView = findViewById(R.id.recycler_view_users)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter(emptyList()) { user ->
            // Handle user click event (if needed)
            // Example: showUserDetails(user)
        }
        recyclerView.adapter = adapter

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().reference.child("users")

        // Fetch user data from Firebase
        fetchDataFromFirebase()

        // Set click listeners for buttons
        val btnCreateUser: Button = findViewById(R.id.btnCreateUser)
        val btnUpdateUser: Button = findViewById(R.id.btnUpdateUser)
        val btnDeleteUser: Button = findViewById(R.id.btnDeleteUser)

        // Set click listeners for buttons
        btnCreateUser.setOnClickListener {
            // Handle create user action
            // Example: createUser()
        }

        btnUpdateUser.setOnClickListener {
            // Handle update user action
            // Example: updateUser()
        }

        btnDeleteUser.setOnClickListener {
            // Handle delete user action
            // Example: deleteUser()
        }
    }

    private fun fetchDataFromFirebase() {
        // Fetching data from Firebase
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userList = mutableListOf<User>()

                for (snapshot in dataSnapshot.children) {
                    val inname = snapshot.child("inname").getValue(String::class.java)
                    val inphoneno = snapshot.child("inphoneno").getValue(String::class.java)
                    val inage = snapshot.child("inage").getValue(String::class.java)
                    val enabled = snapshot.child("enabled").getValue(Boolean::class.java)

                    // Create User objects and add them to the list
                    val user = User(inname ?: "", inphoneno ?: "", inage ?: "", enabled ?: true)
                    userList.add(user)
                }

                // Update the existing adapter data without reassigning the adapter instance
                adapter.setUsers(userList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }
}
