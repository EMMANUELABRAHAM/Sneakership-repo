package com.company.sneakership.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.company.sneakership.R
import com.company.sneakership.databinding.FragmentCartBinding
import com.company.sneakership.ui.viewmodels.CartViewModel

class CartFragment : Fragment() {

    private lateinit var viewModel: CartViewModel
    private lateinit var navController: NavController
    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set up navigation controller
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        // Set up back button click listener
        binding.backButton.setOnClickListener {
            navController.navigateUp()
        }

        return view
    }
}
