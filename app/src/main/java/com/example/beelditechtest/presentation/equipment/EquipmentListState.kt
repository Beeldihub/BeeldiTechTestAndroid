package com.example.beelditechtest.presentation.equipment
import com.example.beelditechtest.domain.model.Equipment

sealed class EquipmentListUiState {
    data object Loading : EquipmentListUiState()
    data class Success(val equipments: List<Equipment>) : EquipmentListUiState()
    data class Error(val message: String) : EquipmentListUiState()
}
