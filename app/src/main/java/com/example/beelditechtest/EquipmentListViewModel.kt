package com.example.beelditechtest

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class EquipmentListViewModel() : ViewModel() {

    private val _state = MutableStateFlow(EquipmentListState())

    init {
        loadEquipments()
    }

    fun loadEquipments() {
        //TODO
    }
}
