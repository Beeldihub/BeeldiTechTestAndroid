package com.example.beelditechtest.presentation.equipment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.beelditechtest.domain.usecase.GetEquipmentsUseCase
import kotlinx.coroutines.flow.asStateFlow

class EquipmentListViewModel(
    private val getEquipmentsUseCase: GetEquipmentsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<EquipmentListUiState>(EquipmentListUiState.Loading)
    val state: StateFlow<EquipmentListUiState> = _state.asStateFlow()

    init {
        loadEquipments()
    }

    fun loadEquipments() {
        viewModelScope.launch {
            _state.value = EquipmentListUiState.Loading
            val result = getEquipmentsUseCase()
            _state.value = if (result.isSuccess) {
                EquipmentListUiState.Success(
                    equipments = result.getOrNull() ?: emptyList()
                )
            } else {
                val exception = result.exceptionOrNull()
                EquipmentListUiState.Error(
                    message = exception?.message ?: "Erreur inconnue"
                )
            }
        }
    }
}
