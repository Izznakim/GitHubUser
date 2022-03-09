package com.example.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.activity.DetailActivity
import com.example.githubuser.databinding.ItemUserBinding
import com.example.githubuser.model.User

class UserAdapter(private val listUser: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .apply(RequestOptions())
                    .into(imgAvatar)

                tvUsername.text = user.username

                itemView.setOnClickListener {
                    Intent(itemView.context, DetailActivity::class.java).also {
                        itemView.context.startActivity(it)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) =
        holder.bind(listUser[position])

    override fun getItemCount(): Int = listUser.size
}