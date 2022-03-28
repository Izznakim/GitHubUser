package com.example.githubuser.ui.favorite

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.data.Result
import com.example.githubuser.data.local.entity.FavoriteEntity
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.ui.ViewModelFactory
import com.example.githubuser.ui.adapter.FavoriteAdapter
import com.example.githubuser.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.list_favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val favoriteViewModel: FavoriteViewModel by viewModels {
            factory
        }

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(true)

        favoriteViewModel.getListFavorite().observe(this) {
            if (it != null) {
                when (it) {
                    is Result.Loading ->
                        binding.progressBar.visibility = View.VISIBLE
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val favorite = it.data
                        binding.rvUser.adapter = setListFavorite(favorite)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Snackbar.make(
                            window.decorView.rootView,
                            "Terjadi kesalahan ${it.error}",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setListFavorite(favorites: List<FavoriteEntity>): FavoriteAdapter {
        val listFavorite = ArrayList<FavoriteEntity>()
        if (favorites.isNotEmpty()) {
            for (favorite in favorites) {
                listFavorite.add(favorite)
            }
        } else {
            binding.tvEmptyData.visibility = View.VISIBLE
        }
        return FavoriteAdapter(listFavorite)
    }
}