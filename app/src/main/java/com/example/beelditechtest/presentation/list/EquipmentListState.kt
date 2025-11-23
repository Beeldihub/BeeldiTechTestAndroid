package com.example.beelditechtest.presentation.list

import com.example.beelditechtest.domain.models.Equipment
/** UI state for the equipment list screen. */

// ADD: New parameters
data class EquipmentListState(
    val allEquipments: List<Equipment> = emptyList(),
    val equipments: List<Equipment> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

