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
import androidx.viewpager2.widget.ViewPager2
import com.company.sneakership.R
import com.company.sneakership.databinding.FragmentDetailBinding
import com.company.sneakership.model.Sneaker
import com.company.sneakership.ui.adapter.SneakerImagePagerAdapter
import com.company.sneakership.ui.viewmodels.DetailViewModel
import com.company.sneakership.ui.viewmodels.SharedViewModel
import com.google.android.material.tabs.TabLayoutMediator

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

        val sneakerId: String = sharedViewModel.selectedItemId.value ?: "0"

        sharedViewModel.getItemDetails(sneakerId)

        sharedViewModel.itemDetails.observe(viewLifecycleOwner) {
            it?.let {
                updateViews(it)
            }
        }
        val initializedList: MutableList<String> = mutableListOf(
            "file:///android_asset/images/sneaker-img.png",
            "file:///android_asset/images/sneaker-img.png",
            "file:///android_asset/images/sneaker-img.png"
        )

        val adapter = SneakerImagePagerAdapter(initializedList)
        binding.viewPager.adapter = adapter
        binding.cartBtn.setOnClickListener {
            showToastForTheCartOperation()
            sharedViewModel.updateCartItem(sneakerId)
            sharedViewModel.getItemDetails(sneakerId)
        }
        // Set up navigation controller
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        // Set up back button click listener
        binding.backButton.setOnClickListener {
            navController.popBackStack()
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
        }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateIconStates()
            }
        })
        binding.leftIcon.setOnClickListener {
            updateViewPagerPosition(isLeft= true)
        }

        binding.rightIcon.setOnClickListener {
            updateViewPagerPosition(false)
        }
        return view
    }

    private fun updateViewPagerPosition(isLeft: Boolean) {
        val currentPosition = binding.viewPager.currentItem
        if (currentPosition > 0 && isLeft) {
            binding.viewPager.currentItem = currentPosition - 1
        }else if (currentPosition < 2 && !isLeft){
            binding.viewPager.currentItem = currentPosition + 1
        }
        updateIconStates()
    }

    private fun updateIconStates() {
        val currentPosition = binding.viewPager.currentItem

        val leftIconResId =
            if (currentPosition == 0) R.drawable.ic_left_inactive else R.drawable.ic_left_active
        binding.leftIcon.setImageResource(leftIconResId)

        val rightIconResId =
            if (currentPosition == 2) R.drawable.right_inactive else R.drawable.right_active
        binding.rightIcon.setImageResource(rightIconResId)
    }

    private fun showToastForTheCartOperation() {
        sharedViewModel.itemDetails.value?.let {
            if (it.addedToCart) {
                Toast.makeText(requireActivity(), "Item Removed", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireActivity(), "Item Added", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateViews(it: Sneaker) {
        binding.tvRetailPrice.text = "$${it.retailPrice.toString()}"
        binding.sneakerName.text = "${it.name} ${it.year}"
        binding.sneakerDesc.text = getString(R.string.sample_description_of_the_item)
        if (it.addedToCart) {
            binding.cartBtn.text = getString(R.string.remove_from_cart)
        } else {
            binding.cartBtn.text = getString(R.string.add_to_cart_text)
        }
    }
}
