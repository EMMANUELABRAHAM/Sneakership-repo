package com.company.sneakership.ui.viewmodels

import androidx.lifecycle.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.company.sneakership.model.Sneaker

class HomeViewModel : ViewModel() {

    private val _sneakersListLiveData = MutableLiveData<List<Sneaker>>()
    val sneakerListLiveData: LiveData<List<Sneaker>>
        get() = _sneakersListLiveData

    // Initialize the ViewModel with some dummy data
    init {
        loadSneakers()
    }

    private fun loadSneakers() {
        // In a real application, you would fetch data from a repository or API
        val dummyData = listOf(
            Sneaker(id = 1, name = "T-shirt 1", price = 19.99),
            Sneaker(id = 2, name = "T-shirt 2", price = 24.99),
            Sneaker(id = 3, name = "T-shirt 3", price = 29.99),
            // Add more T-shirts as needed
        )

        _sneakersListLiveData.value = dummyData
    }
}
