package com.company.sneakership.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.company.sneakership.R
import com.company.sneakership.databinding.FragmentCartBinding
import com.company.sneakership.ui.adapter.CartSneakerListAdapter
import com.company.sneakership.ui.adapter.SneakerAdapter
import com.company.sneakership.ui.viewmodels.CartViewModel
import com.company.sneakership.ui.viewmodels.HomeViewModel
import com.company.sneakership.ui.viewmodels.SharedViewModel

class CartFragment : Fragment() {

    private lateinit var viewModel: CartViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var navController: NavController
    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        binding.backButton.setOnClickListener {
            navController.navigateUp()
        }
        sharedViewModel = ViewModelProvider(
            requireActivity(),
            SharedViewModel.ViewModelFactory(requireActivity().application)
        )[SharedViewModel::class.java]
        viewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]

        binding.recyclerViewCart.layoutManager = GridLayoutManager(requireContext(), 1)
        val adapter = CartSneakerListAdapter { itemId ->
            sharedViewModel.setSelectedItemId(itemId)
        }
        binding.recyclerViewCart.adapter = adapter

        sharedViewModel.updateCartList()
        sharedViewModel.sneakersCartListLiveData.observe(viewLifecycleOwner){
            it?.let {
                adapter.submitList(it)
            }
        }
        return view
    }
}
