package com.example.loginapp.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.databinding.ListItemsBinding
import com.example.loginapp.db.User

class UserAdapter(private val onDeleteClick: (User) -> Unit) :
    ListAdapter<User, UserAdapter.UserViewHolder>(DiffCallback()) {

    class UserViewHolder(val binding: ListItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)


    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.binding.name.text = user.username
        holder.binding.pass.text = user.password

        holder.binding.delete.setOnClickListener {
            onDeleteClick(user)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
    }

}
