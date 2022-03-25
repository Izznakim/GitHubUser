package com.example.githubuser.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.data.local.entity.FavoriteEntity
import com.example.githubuser.databinding.ItemUserBinding
import com.example.githubuser.ui.detail.DetailActivity

class FavoriteAdapter(private val listFavorite: List<FavoriteEntity>) :
    RecyclerView.Adapter<FavoriteAdapter.UserViewHolder>() {
    class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: FavoriteEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(favorite.avatarUrl)
                    .apply(RequestOptions())
                    .into(imgAvatar)

                tvUsername.text = favorite.username

                itemView.setOnClickListener {
                    Intent(itemView.context, DetailActivity::class.java).also {
                        it.putExtra(DetailActivity.EXTRA_USER, favorite)
                        it.putExtra(DetailActivity.EXTRA_ISFAVORITE, true)
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
        holder.bind(listFavorite[position])

    override fun getItemCount(): Int = listFavorite.size
}