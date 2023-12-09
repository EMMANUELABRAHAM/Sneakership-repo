package com.company.sneakership.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.company.sneakership.R
import com.company.sneakership.databinding.FragmentDetailBinding
import com.company.sneakership.ui.viewmodels.DetailViewModel
import com.company.sneakership.ui.viewmodels.SharedViewModel

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var navController: NavController
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        // Retrieve itemId from SharedViewModel
        val itemId = sharedViewModel.selectedItemId.value ?: 0

        // Use itemId to load details from ViewModel
        viewModel.loadDetails(itemId)

        // Set up navigation controller
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        // Set up back button click listener
        binding.backButton.setOnClickListener {
            navController.navigateUp()
        }

        return view
    }
}
