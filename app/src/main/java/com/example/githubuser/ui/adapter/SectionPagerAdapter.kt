package com.example.githubuser.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.ui.detail.FollFragment
import com.example.githubuser.ui.detail.FollFragment.Companion.ARG_SECTION_NUMBER
import com.example.githubuser.ui.detail.FollFragment.Companion.USERNAME

class SectionPagerAdapter(activity: AppCompatActivity) :
    FragmentStateAdapter(activity) {
    var username: String? = null

    override fun createFragment(position: Int): Fragment {
        val fragment = FollFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_SECTION_NUMBER, position)
            putString(USERNAME, username)
        }
        return fragment
    }

    override fun getItemCount(): Int = 2
}