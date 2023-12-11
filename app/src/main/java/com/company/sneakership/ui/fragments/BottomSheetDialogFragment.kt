package com.company.sneakership.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.company.sneakership.R
import com.company.sneakership.databinding.FragmentSortBottomSheetBinding
import com.company.sneakership.model.SortCriteria
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentSortBottomSheetBinding? = null
    private val binding get() = _binding!!

    interface SortListener {
        fun onSortSelected(sortCriteria: SortCriteria)
    }

    private var sortListener: SortListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSortBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnApply.setOnClickListener {
            val selectedRadioButtonId = binding.radioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                val selectedSortCriteria = when (selectedRadioButtonId) {
                    R.id.radioButtonLowToHigh -> SortCriteria.RETAIL_PRICE_LOW_TO_HIGH
                    R.id.radioButtonHighToLow -> SortCriteria.RETAIL_PRICE_HIGH_TO_LOW
                    else -> throw IllegalArgumentException("Invalid radio button ID")
                }
                sortListener?.onSortSelected(selectedSortCriteria)
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setSortListener(listener: SortListener) {
        sortListener = listener
    }
}
