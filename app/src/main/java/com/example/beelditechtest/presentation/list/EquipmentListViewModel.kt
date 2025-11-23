package com.example.beelditechtest.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beelditechtest.UserRole
import com.example.beelditechtest.domain.models.Equipment
import com.example.beelditechtest.domain.usecases.GetEquipmentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
/** Handles equipment loading and exposes UI state. */
class EquipmentListViewModel(
    private val getEquipmentsUseCase: GetEquipmentsUseCase
) : ViewModel() {
    private val _selectedRole = MutableStateFlow(UserRole.ADMIN)
    val selectedRole = _selectedRole.asStateFlow()

    fun setRole(role: UserRole) {
        _selectedRole.value = role
        applyFiltering()
    }
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
                            allEquipments = equipments,   // complete list
                            equipments = equipments,       // filtered list
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

    // ADD: Filtering function
    private fun applyFiltering() {
        val all = _state.value.allEquipments
        val role = _selectedRole.value

        val filtered = when (role) {
            UserRole.ADMIN -> all
            UserRole.MAINTAINER -> all.filter { it.type == 0 || it.type == 1 }
            UserRole.AUDITOR -> all.filter { it.type == 0 }
        }

        _state.value = _state.value.copy(
            equipments = filtered
        )
    }

}
