package com.example.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
    private var userList: List<User>,
    private val onUserClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.bind(currentUser)
    }

    override fun getItemCount(): Int = userList.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.findViewById<TextView>(R.id.textViewUserName).text = user.inname
            // Handle user click event (if needed)
            itemView.setOnClickListener { onUserClick(user) }
            // Handle checkbox state change (if needed)
            val checkBoxUserSelection = itemView.findViewById<CheckBox>(R.id.checkBoxUserSelection)
            checkBoxUserSelection.setOnCheckedChangeListener { _, isChecked ->
                // Handle checkbox state change
                // Example: handleCheckboxSelection(user, isChecked)
            }
        }
    }
}
