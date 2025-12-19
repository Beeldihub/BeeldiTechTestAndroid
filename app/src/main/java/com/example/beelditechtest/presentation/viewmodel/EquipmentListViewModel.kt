package com.example.beelditechtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beelditechtest.presentation.state.EquipmentListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EquipmentListViewModel() : ViewModel() {

    private val _equipments = MutableStateFlow(EquipmentListState())
    val equipments = _equipments.asStateFlow()


    init {
        loadEquipments()
    }

    fun loadEquipments() {
        viewModelScope.launch {

        }
    }
}