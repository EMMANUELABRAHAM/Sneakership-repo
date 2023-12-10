package com.company.sneakership.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.company.sneakership.model.Sneaker
import com.company.sneakership.model.repository.SneakerRepository
import com.company.sneakership.utils.ApiResponse
import kotlinx.coroutines.launch

class SharedViewModel(private val application: Application) : ViewModel() {
    private val _selectedItemId = MutableLiveData<String>()
    val selectedItemId: LiveData<String>
        get() = _selectedItemId
    private val sneakerRepository = SneakerRepository()
    private val _sneakersListLiveData = MutableLiveData<List<Sneaker>?>()
    val sneakersListLiveData: MutableLiveData<List<Sneaker>?> = _sneakersListLiveData

    private val _errorMsg: MutableLiveData<String?> = MutableLiveData<String?>()
    val errorMsg: LiveData<String?>
        get() = _errorMsg


    init {
        getSneakers()
    }

    private fun getSneakers() {
        viewModelScope.launch {
            try {
                when (val response = sneakerRepository.getSneakers(application.assets)) {
                    is ApiResponse.Success -> {
                        // Update UI with successful response data
                        _sneakersListLiveData.value = response.data
                    }
                    is ApiResponse.Error -> {
                        _errorMsg.value = response.errorMessage
                    }
                }
            } catch (e: Exception) {
                val errorMessage = "Unexpected error: ${e.message}"
                _errorMsg.value = errorMessage
            }
        }
    }

    fun setSelectedItemId(itemId: String) {
        _selectedItemId.value = itemId
    }


    class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
                return SharedViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}