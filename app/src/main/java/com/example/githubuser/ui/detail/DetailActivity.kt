package com.example.githubuser.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.R
import com.example.githubuser.data.local.entity.FavoriteEntity
import com.example.githubuser.data.remote.response.User
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.ui.ViewModelFactory
import com.example.githubuser.ui.adapter.SectionPagerAdapter
import com.example.githubuser.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private var isBeenHere: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detailTitle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val detailViewModel: DetailViewModel by viewModels {
            factory
        }

        if (savedInstanceState != null) {
            val result = savedInstanceState.getBoolean(STATE_RESULT)
            isBeenHere = result
        }

        val isFavorite = intent.getBooleanExtra(EXTRA_ISFAVORITE, false)

        if (!isFavorite) {
            val user = intent.getParcelableExtra<User>(EXTRA_USER)
            if (user != null) {
                getDetailUser(user, null, detailViewModel)
                viewPager(user.username)
            }
        } else {
            val favorite = intent.getParcelableExtra<FavoriteEntity>(EXTRA_USER)
            if (favorite != null) {
                getDetailUser(null, favorite, detailViewModel)
                viewPager(favorite.username)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.setting_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                MainActivity.toSettings(this)
                true
            }
            android.R.id.home -> {
                onSupportNavigateUp()
            }
            else -> true
        }
    }

    private fun viewPager(username: String?) {
        val sectionsPagerAdapter = SectionPagerAdapter(this)
        sectionsPagerAdapter.username = username
        with(binding) {
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITTLES[position])
            }.attach()

            supportActionBar?.elevation = 0f
        }
    }

    private fun getDetailUser(
        user: User? = null,
        favoriteEntity: FavoriteEntity? = null,
        detailViewModel: DetailViewModel
    ) {
        var favorite = false
        val username = user?.username ?: favoriteEntity?.username
        val avatarUrl = user?.avatarUrl ?: favoriteEntity?.avatarUrl
        with(binding) {
            Glide.with(this@DetailActivity)
                .load(avatarUrl)
                .apply(RequestOptions())
                .into(imgAvatar)

            tvUsername.text = username

            if (!isBeenHere) {
                detailViewModel.getDetailUser(tvUsername.text.toString())
                isBeenHere = true
            }
            detailViewModel.detailUser.observe(this@DetailActivity) { user ->
                tvName.text = user?.name ?: "\"Data Kosong\""
                tvFol.text =
                    getString(R.string.textfol, user?.followers, user?.following)
                tvRepo.text = getString(R.string.textrepo, user?.repository)
                tvCompRepo.text = user?.company ?: "\"Data Kosong\""
                tvLocation.text = user?.location ?: "\"Data Kosong\""
            }

            detailViewModel.isLoading.observe(this@DetailActivity) {
                showLoading(it)
            }

            detailViewModel.snackbarText.observe(this@DetailActivity) {
                it.getContentIfNotHandled()?.let { snackBarText ->
                    Snackbar.make(window.decorView.rootView, snackBarText, Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

            username?.let { detailViewModel.checkExistOrNot(it) }
            detailViewModel.isFavorite.observe(this@DetailActivity) { isFavorite ->
                if (isFavorite) {
                    favorite=true
                    binding.ivFavorite.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.ic_favorited,null))
                } else {
                    favorite=false
                    binding.ivFavorite.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite, null))
                }
            }

            binding.ivFavorite.setOnClickListener {
                if (username != null) {
                    favorite = if (favorite) {
                        detailViewModel.deleteUserFromFavorite(username)
                        binding.ivFavorite.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.ic_favorite,
                                null
                            )
                        )
                        false
                    } else {
                        if (user != null || favoriteEntity != null) {
                            detailViewModel.setUserToFavorite(user, favoriteEntity)
                        }
                        binding.ivFavorite.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.ic_favorited,
                                null
                            )
                        )
                        true
                    }
                }
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
        const val EXTRA_ISFAVORITE = "extra_isfavorite"
        const val STATE_RESULT = "state_result"

        @StringRes
        private val TAB_TITTLES = intArrayOf(
            R.string.follower, R.string.following
        )
    }
}