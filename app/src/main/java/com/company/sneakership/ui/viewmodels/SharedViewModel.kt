package com.company.sneakership.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.company.sneakership.model.OrderDetails
import com.company.sneakership.model.Sneaker
import com.company.sneakership.model.SortCriteria
import com.company.sneakership.model.repository.SneakerRepository
import com.company.sneakership.utils.ApiResponse
import kotlinx.coroutines.launch
import kotlin.math.abs

class SharedViewModel(private val application: Application) : ViewModel() {
    private val _selectedItemId = MutableLiveData<String>()
    val selectedItemId: LiveData<String>
        get() = _selectedItemId
    private val sneakerRepository = SneakerRepository()
    private val _sneakersListLiveData = MutableLiveData<List<Sneaker>?>()
    val sneakersListLiveData: MutableLiveData<List<Sneaker>?> = _sneakersListLiveData

    private val _sneakersCartListLiveData = MutableLiveData<List<Sneaker>?>()
    val sneakersCartListLiveData: MutableLiveData<List<Sneaker>?> = _sneakersCartListLiveData

    private val _errorMsg: MutableLiveData<String?> = MutableLiveData<String?>()
    val errorMsg: LiveData<String?>
        get() = _errorMsg
    private val _itemDetails = MutableLiveData<Sneaker?>()
    val itemDetails: LiveData<Sneaker?>
        get() = _itemDetails

    private val _orderDetails: MutableLiveData<OrderDetails> = MutableLiveData<OrderDetails>()
    val orderDetails: LiveData<OrderDetails>
        get() = _orderDetails

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

    fun getItemDetails(itemId: String) {
        _itemDetails.value = sneakersListLiveData.value?.first { it.id == itemId }
    }



    fun updateCartItem(id: String){
       sneakersListLiveData.value?.first { it.id == id }?.let {sneaker ->
           sneaker.addedToCart.let { addedToCart ->
               sneaker.addedToCart = !addedToCart
           }
       }
        updateCartList()
    }

    fun updateCartList(){
        _sneakersCartListLiveData.value = sneakersListLiveData.value?.filter {
            it.addedToCart
        }
        updateOrderDetails()
    }

    private fun updateOrderDetails() {
        val subTotal:Int = getSubtotalPrice()
        val taxAndCharges = abs( subTotal * TAX)
        val total = subTotal + taxAndCharges

        _orderDetails.value = OrderDetails(subTotal.toString(),taxAndCharges.toString(),total.toString())
    }

    private fun getSubtotalPrice(): Int {
        var subTotal = 0
        _sneakersCartListLiveData.value?.forEach { sneaker ->
            sneaker.retailPrice?.let{
                subTotal += it
            }
        }
        return subTotal
    }

    fun searchSneakers(searchWord: String) {
        //Home ViewModel
        _sneakersListLiveData.value = sneakersListLiveData.value?.filter {
            it.name?.contains(searchWord) ?: false
        }
    }

    fun sortSneakersBy(criteria: SortCriteria) {
        //Home View Model
        val sortedList = when (criteria) {
            SortCriteria.RETAIL_PRICE_LOW_TO_HIGH -> sneakersListLiveData.value?.sortedBy { it.retailPrice }
            SortCriteria.RETAIL_PRICE_HIGH_TO_LOW -> sneakersListLiveData.value?.sortedByDescending { it.retailPrice }
        }
        _sneakersListLiveData.value = sortedList
    }

    class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
                return SharedViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        const val TAX:Double = 0.18
    }
}