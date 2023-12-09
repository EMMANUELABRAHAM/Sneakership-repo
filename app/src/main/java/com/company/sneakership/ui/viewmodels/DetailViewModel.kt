package com.company.sneakership.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.sneakership.model.SneakerDetails

class DetailViewModel : ViewModel() {

    // LiveData for the details of the selected T-shirt
    private val _details = MutableLiveData<SneakerDetails>()
    val details: LiveData<SneakerDetails>
        get() = _details

    // Method to load details based on itemId
    fun loadDetails(itemId: Int) {
        // TODO: Implement logic to load details from repository based on itemId
    }
}
