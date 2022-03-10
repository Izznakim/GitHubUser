package com.example.githubuser.main

import android.app.SearchManager
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.model.User

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title="List User"

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(true)

        mainViewModel.listUser.observe(this){
            setListUser(it)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater=menuInflater
        inflater.inflate(R.menu.option_menu,menu)

        val searchManager= getSystemService<SearchManager>()
        val searchView=menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager?.getSearchableInfo(componentName))
        searchView.queryHint=resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                mainViewModel.getSearchUser(query)
                mainViewModel.listSearchUser.observe(this@MainActivity){
                    setListUser(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressBar.visibility= View.VISIBLE
            binding.rvUser.visibility= View.GONE
        }else{
            binding.progressBar.visibility=View.GONE
            binding.rvUser.visibility= View.VISIBLE
        }
    }

    private fun setListUser(users:List<User>) {
        val listUser=ArrayList<User>()
        for (user in users){
            listUser.add(user)
        }
        val userAdapter = UserAdapter(listUser)
        binding.rvUser.adapter = userAdapter
    }
}