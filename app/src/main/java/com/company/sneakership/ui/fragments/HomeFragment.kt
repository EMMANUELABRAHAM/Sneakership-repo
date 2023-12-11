package com.company.sneakership.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.company.sneakership.R
import com.company.sneakership.databinding.FragmentHomeBinding
import com.company.sneakership.model.SortCriteria
import com.company.sneakership.ui.adapter.SneakerAdapter
import com.company.sneakership.ui.adapter.listners.HomeSneakerListListener
import com.company.sneakership.ui.viewmodels.HomeViewModel
import com.company.sneakership.ui.viewmodels.SharedViewModel

class HomeFragment : Fragment(), HomeSneakerListListener, SortBottomSheetFragment.SortListener {

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

        sharedViewModel = ViewModelProvider(
            requireActivity(),
            SharedViewModel.ViewModelFactory(requireActivity().application)
        )[SharedViewModel::class.java]

        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = SneakerAdapter()
        adapter.setOnItemClickListener(this)
        binding.recyclerView.adapter = adapter

        sharedViewModel.sneakersListLiveData.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        sharedViewModel.errorMsg.observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        }

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        configureSearchView()

        val sortBottomSheetFragment = SortBottomSheetFragment()
        sortBottomSheetFragment.setSortListener(this)
        binding.textSortBy.setOnClickListener {
            sortBottomSheetFragment.show(childFragmentManager, sortBottomSheetFragment.tag)

        }
        return view
    }

    private fun configureSearchView() {
        // Set listeners for SearchView
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                sharedViewModel.searchSneakers(newText.orEmpty())
                return false
            }
        })
    }

    private fun navigateToDetail() {
        navController.navigate(R.id.action_homeFragment_to_detailFragment)
    }

    override fun itemClick(id: String) {
        sharedViewModel.setSelectedItemId(id)
        navigateToDetail()
    }

    override fun cartIconClick(id: String) {
      sharedViewModel.updateCartItem(id)
    }

    override fun onSortSelected(sortCriteria: SortCriteria) {
        sharedViewModel.sortSneakersBy(sortCriteria)
        val text = when(sortCriteria){
            SortCriteria.RETAIL_PRICE_HIGH_TO_LOW -> "HighToLow"
            SortCriteria.RETAIL_PRICE_LOW_TO_HIGH -> "LowToHigh"
        }
        binding.textSortBy.text =text
    }
}
