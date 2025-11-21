package com.example.beelditechtest.presentation.equipment
import com.example.beelditechtest.domain.model.EquipmentUiModel

sealed class EquipmentScreenUiModel {
    data object Loading : EquipmentScreenUiModel()
    data class Success(val equipmentUiModels: List<EquipmentUiModel>) : EquipmentScreenUiModel()
    data class Error(val message: String) : EquipmentScreenUiModel()
}
