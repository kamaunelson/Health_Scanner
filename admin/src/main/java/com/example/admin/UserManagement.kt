package com.example.admin

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UserManagement : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_management)
        recyclerView = findViewById(R.id.recycler_view_users)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter(getSampleUserList()) { user ->
            // Handle user click event (if needed)
            // Example: showUserDetails(user)
        }
        recyclerView.adapter = adapter

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

    private fun getSampleUserList(): List<User> {
        // Replace this with actual user data fetched from Firebase or elsewhere
        return listOf(
            User("John Doe"),
            User("Alice Smith"),
            User("Bob Johnson")
            // Add more users here
        )
    }
}