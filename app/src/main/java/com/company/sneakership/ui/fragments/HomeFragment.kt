package com.company.sneakership.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
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

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        // Setup RecyclerView and adapter for listing T-shirts
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = SneakerAdapter { itemId ->
            sharedViewModel.setSelectedItemId(itemId)
            navigateToDetail()
        }
        binding.recyclerView.adapter = adapter

        // Observe the list of T-shirts from the ViewModel
        viewModel.sneakerListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        // Set up navigation controller
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        return view
    }

    private fun navigateToDetail() {
        navController.navigate(R.id.action_homeFragment_to_detailFragment)
    }
}
