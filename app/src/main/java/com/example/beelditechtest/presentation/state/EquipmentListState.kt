package com.example.beelditechtest.presentation.state

import com.example.beelditechtest.domain.model.Equipment

sealed class EquipmentListState {
    data object Loading : EquipmentListState()
    data class Success(val equipments: List<Equipment>) : EquipmentListState()
    data class Error(val message: String) : EquipmentListState()
}