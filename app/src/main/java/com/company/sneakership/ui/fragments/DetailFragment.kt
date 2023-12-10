package com.company.sneakership.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.company.sneakership.R
import com.company.sneakership.databinding.FragmentDetailBinding
import com.company.sneakership.model.Sneaker
import com.company.sneakership.ui.adapter.SneakerImagePagerAdapter
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
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        sharedViewModel = ViewModelProvider(
            requireActivity(),
            SharedViewModel.ViewModelFactory(requireActivity().application)
        )[SharedViewModel::class.java]

        // Retrieve itemId from SharedViewModel
        val sneakerId: String = sharedViewModel.selectedItemId.value ?: "0"

        // Use itemId to load details from ViewModel
        sharedViewModel.getItemDetails(sneakerId)

        sharedViewModel.itemDetails.observe(viewLifecycleOwner) {
            it?.let {
                updateViews(it)
                Toast.makeText(requireActivity(), "UpdateView", Toast.LENGTH_SHORT).show()
            }
        }

        val initializedList: MutableList<String> = mutableListOf("file:///android_asset/images/sneaker-img.png", "file:///android_asset/images/sneaker-img.png", "file:///android_asset/images/sneaker-img.png")


        val adapter = SneakerImagePagerAdapter(initializedList)
        binding.viewPager.adapter = adapter
        binding.cartBtn.setOnClickListener{
            sharedViewModel.updateCartList(sneakerId)
            Toast.makeText(requireActivity(), "Item carted", Toast.LENGTH_SHORT).show()
            //TODO change the Button Text.
        }
        // Set up navigation controller
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        // Set up back button click listener
        binding.backButton.setOnClickListener {
            Toast.makeText(requireActivity(), "Back Button Pressed", Toast.LENGTH_SHORT).show()
            navController.navigateUp()
        }

        return view
    }

    private fun updateViews(it: Sneaker) {
        binding.tvRetailPrice.text = "$${it.retailPrice.toString()}"
        binding.sneakerName.text = it.name
        binding.sneakerDesc.text = getString(R.string.sample_description_of_the_item)
    }
}
