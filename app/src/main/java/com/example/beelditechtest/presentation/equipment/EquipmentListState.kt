package com.example.beelditechtest.presentation.equipment
import com.example.beelditechtest.domain.model.Equipment

data class EquipmentListState(
    val equipmentEntities: List<Equipment> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
