package com.company.sneakership.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.company.sneakership.model.Sneaker
import com.company.sneakership.model.repository.SneakerRepository
import com.company.sneakership.utils.ApiResponse
import kotlinx.coroutines.launch

class HomeViewModel(private val application: Application) : ViewModel() {

    private val sneakerRepository = SneakerRepository()
    private val _sneakersLiveData = MutableLiveData<ApiResponse<List<Sneaker>>>()
    val sneakersLiveData: LiveData<ApiResponse<List<Sneaker>>> = _sneakersLiveData

    init {
        getSneakers()
    }

    private fun getSneakers() {
        viewModelScope.launch {
            try {
                when (val response = sneakerRepository.getSneakers(application.assets)) {
                    is ApiResponse.Success -> {
                        // Update UI with successful response data
                        _sneakersLiveData.value = ApiResponse.Success(response.data)
                    }

                    is ApiResponse.Error -> {
                        // Handle the error case
                        val errorMessage = response.errorMessage
                        val statusCode = response.statusCode
                        _sneakersLiveData.value = ApiResponse.Error(errorMessage, statusCode)
                    }
                }
            } catch (e: Exception) {
                // Handle unexpected exceptions
                val errorMessage = "Unexpected error: ${e.message}"
                _sneakersLiveData.value = ApiResponse.Error(errorMessage, 500)
            }
        }
    }

    class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
