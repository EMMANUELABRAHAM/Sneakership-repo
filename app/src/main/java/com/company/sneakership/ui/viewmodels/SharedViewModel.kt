package com.company.sneakership.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _selectedItemId = MutableLiveData<String>()
    val selectedItemId: LiveData<String>
        get() = _selectedItemId

    fun setSelectedItemId(itemId: String) {
        _selectedItemId.value = itemId
    }
}
