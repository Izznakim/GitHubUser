package com.example.githubuser.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.FragmentFollBinding
import com.example.githubuser.ui.ViewModelFactory
import com.example.githubuser.ui.main.MainActivity

class FollFragment : Fragment() {

    private var _binding: FragmentFollBinding? = null
    private val binding get() = _binding!!

    private var isBeenHere: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        val detailViewModel: DetailViewModel by viewModels {
            factory
        }

        if (savedInstanceState != null) {
            val result = savedInstanceState.getBoolean(DetailActivity.STATE_RESULT)
            isBeenHere = result
        }

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = arguments?.getString(USERNAME)

        binding.rvUser.layoutManager = LinearLayoutManager(context)
        binding.rvUser.setHasFixedSize(true)

        if (!isBeenHere) {
            if (index == 0) {
                detailViewModel.getFollower(username.toString())
            } else {
                detailViewModel.getFollowing(username.toString())
            }
            isBeenHere = true
        }

        detailViewModel.listFoll.observe(viewLifecycleOwner) {
            binding.rvUser.adapter = MainActivity.setListUser(it)
            if (it.isEmpty()) {
                binding.tvEmptyData.visibility = View.VISIBLE
            } else {
                binding.tvEmptyData.visibility = View.GONE
            }
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(DetailActivity.STATE_RESULT, isBeenHere)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvUser.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvUser.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val USERNAME = "username"
    }
}