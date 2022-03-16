package com.example.githubuser.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.model.User

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    private var isBeenHere: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState != null) {
            val result = savedInstanceState.getBoolean(STATE_RESULT)
            isBeenHere = result
        }

        val user = intent.getParcelableExtra<User>(EXTRA_USER)
        Log.d(TAG, "onViewCreated: $user")

        with(binding) {
            Glide.with(this@DetailActivity)
                .load(user?.avatarUrl)
                .apply(RequestOptions())
                .into(imgAvatar)

            tvUsername.text = user?.username

            if (!isBeenHere) {
                detailViewModel.getDetailUser(tvUsername.text.toString())
                isBeenHere = true
            }
            detailViewModel.detailUser.observe(this@DetailActivity) { user ->
                tvName.text = user?.name ?: "null"
                tvFol.text =
                    getString(R.string.textfol, user?.followers, user?.following)
                tvRepo.text=getString(R.string.textrepo,user?.repository)
                tvCompRepo.text = user?.company ?: "null"
                tvLocation.text = user?.location ?: "null"
            }

            detailViewModel.isLoading.observe(this@DetailActivity) {
                showLoading(it)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_RESULT, isBeenHere)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.contentContainer.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.contentContainer.visibility = View.VISIBLE
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        private const val STATE_RESULT = "state_result"
        private const val TAG = "DetailActivity"
    }
}