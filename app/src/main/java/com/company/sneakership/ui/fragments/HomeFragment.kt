package com.company.sneakership.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.company.sneakership.R
import com.company.sneakership.databinding.FragmentHomeBinding
import com.company.sneakership.ui.adapter.SneakerAdapter
import com.company.sneakership.ui.viewmodels.HomeViewModel
import com.company.sneakership.ui.viewmodels.SharedViewModel


class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var navController: NavController
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

//        Glide
//            .with(this)
//            .load("file:///android_asset/images/sneaker.png")
//            .centerCrop()
//            .placeholder(R.drawable.ic_home)
//            .into(binding.imageView);

//        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        viewModel = ViewModelProvider(
            this,
            HomeViewModel.ViewModelFactory(requireActivity().application)
        )[HomeViewModel::class.java]

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = SneakerAdapter { itemId ->
            sharedViewModel.setSelectedItemId(itemId)
            navigateToDetail()
        }
        binding.recyclerView.adapter = adapter

        viewModel.sneakersLiveData.observe(viewLifecycleOwner, Observer {
            Log.d("Response", it.toString())
        })

        // Set up navigation controller
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)


//        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
//        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)

        binding.searchView.setOnClickListener {
            Toast.makeText(requireActivity(), "Search", Toast.LENGTH_SHORT).show()
        }
        configureSearchView()
        return view
    }

    private fun configureSearchView() {
        // Set listeners for SearchView
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle the query when the user submits
                binding.searchView.clearFocus()
//                binding.searchView.isIconified = true
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle the query text as it changes
                return false
            }
        })

//        // Set the close listener for SearchView
//        binding.searchView.setOnCloseListener {
//            // Handle the search view being closed
//            false
//        }
    }

    private fun navigateToDetail() {
        navController.navigate(R.id.action_homeFragment_to_detailFragment)
    }
}
