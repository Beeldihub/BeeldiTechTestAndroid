package com.example.beelditechtest

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class EquipmentListViewModel(
    private val dataSource: EquipmentDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(EquipmentListState())
    val state: StateFlow<EquipmentListState> = _state

    init {
        loadEquipments()
    }

    fun loadEquipments() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val equipments = dataSource.getEquipments()
                _state.value = _state.value.copy(
                    equipmentEntities = equipments,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Erreur inconnue"
                )
            }
        }
    }
}
