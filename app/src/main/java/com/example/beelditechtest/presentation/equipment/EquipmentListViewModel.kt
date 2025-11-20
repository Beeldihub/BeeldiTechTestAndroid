package com.example.beelditechtest.presentation.equipment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.beelditechtest.domain.usecase.GetEquipmentsUseCase

class EquipmentListViewModel(
    private val getEquipmentsUseCase: GetEquipmentsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EquipmentListState())
    val state: StateFlow<EquipmentListState> = _state

    init {
        loadEquipments()
    }

    fun loadEquipments() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val result = getEquipmentsUseCase()
            if (result.isSuccess) {
                _state.value = _state.value.copy(
                    equipmentEntities = result.getOrNull() ?: emptyList(),
                    isLoading = false
                )
            } else {
                val exception = result.exceptionOrNull()
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = exception?.message ?: "Erreur inconnue"
                )
            }
        }
    }
}
