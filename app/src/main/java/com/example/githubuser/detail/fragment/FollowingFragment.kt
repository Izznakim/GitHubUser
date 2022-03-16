package com.example.githubuser.detail.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.FragmentFollowingBinding
import com.example.githubuser.detail.DetailActivity
import com.example.githubuser.detail.DetailViewModel
import com.example.githubuser.main.MainActivity

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding?=null
    private val binding get() = _binding!!
    private val detailViewModel by viewModels<DetailViewModel>()

    private var isBeenHere: Boolean = false
    private var username:String?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentFollowingBinding.inflate(inflater,container,false)
        username=arguments?.getString(USERNAME)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            val result = savedInstanceState.getBoolean(DetailActivity.STATE_RESULT)
            isBeenHere = result
        }

        binding.rvUser.layoutManager = LinearLayoutManager(context)
        binding.rvUser.setHasFixedSize(true)

        if (!isBeenHere) {
            detailViewModel.getFollowing(username.toString())
            isBeenHere = true
        }
        Log.d(TAG, "onViewCreated: Following > $username")

        detailViewModel.listFollowing.observe(viewLifecycleOwner){
            binding.rvUser.adapter= MainActivity.setListUser(it)
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(DetailActivity.STATE_RESULT, isBeenHere)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    companion object{
        const val ARG_SECTION_NUMBER="section_number"
        private const val USERNAME="username"
        private const val TAG = "FollowerFragment"

        fun newInstance(username:String):FollowingFragment{
            val fragment=FollowingFragment()

            val bundle=Bundle()
            bundle.putString(USERNAME,username)
            fragment.arguments=bundle
            return fragment
        }
    }
}