package com.example.beelditechtest.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beelditechtest.domain.models.Equipment
import com.example.beelditechtest.domain.usecases.GetEquipmentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
/** Handles equipment loading and exposes UI state. */
class EquipmentListViewModel(
    private val getEquipmentsUseCase: GetEquipmentsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EquipmentListState(isLoading = true))
    val state: StateFlow<EquipmentListState> = _state

    init {
        loadEquipments()
    }

    fun loadEquipments() {
        viewModelScope.launch {
            getEquipmentsUseCase()
                .onStart {
                    _state.update { it.copy(isLoading = true, error = null) }
                }
                .catch { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: "Erreur inconnue"
                        )
                    }
                }
                .collect { equipments ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            equipments = equipments,
                            error = null
                        )
                    }
                }
        }
    }
    // ADD : Function to find equipment by id
    fun getEquipmentById(id: String): Equipment? {
        return state.value.equipments.find { it.id == id }
    }

}
