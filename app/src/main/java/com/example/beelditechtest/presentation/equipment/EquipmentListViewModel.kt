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

    private val _state = MutableStateFlow<EquipmentScreenUiModel>(EquipmentScreenUiModel.Loading)
    val state: StateFlow<EquipmentScreenUiModel> = _state.asStateFlow()

    init {
        loadEquipments()
    }

    fun loadEquipments() {
        viewModelScope.launch {
            _state.value = EquipmentScreenUiModel.Loading
            val result = getEquipmentsUseCase()
            _state.value = if (result.isSuccess) {
                EquipmentScreenUiModel.Success(
                    equipmentUiModels = result.getOrNull() ?: emptyList()
                )
            } else {
                val exception = result.exceptionOrNull()
                EquipmentScreenUiModel.Error(
                    message = exception?.message ?: "Erreur inconnue"
                )
            }
        }
    }
}
