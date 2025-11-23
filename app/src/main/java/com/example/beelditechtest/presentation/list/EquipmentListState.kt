package com.example.beelditechtest.presentation.list

import com.example.beelditechtest.domain.models.Equipment
/** UI state for the equipment list screen. */
data class EquipmentListState(
    val equipments: List<Equipment> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
