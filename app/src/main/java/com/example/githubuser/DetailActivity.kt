package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object{
        const val EXTRA_USER="extra_user"
        private const val TAG = "DetailActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user=intent.getParcelableExtra<User>(EXTRA_USER)
        Log.d(TAG, "onViewCreated: $user")

        with(binding) {
            Glide.with(this@DetailActivity)
                .load(user?.avatar)
                .apply(RequestOptions())
                .into(imgAvatar)

            tvUsername.text=user?.username
            tvName.text=user?.name
            tvFol.text=getString(R.string.textfol, user?.followers, user?.following, user?.repository)
            tvCompRepo.text=user?.company
            tvLocation.text=user?.location
        }
    }
}