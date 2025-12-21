package com.example.beelditechtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beelditechtest.domain.usecase.EquipmentUseCase
import com.example.beelditechtest.presentation.state.EquipmentDetailState
import com.example.beelditechtest.presentation.state.UiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.Int

@HiltViewModel(assistedFactory = EquipmentDetailViewModelFactory::class)
class EquipmentDetailViewModel
    @AssistedInject
    constructor(
        @Assisted
        val equipmentId: Int,
        private val equipmentUseCase: EquipmentUseCase,
    ) : ViewModel() {
        private val _equipment = MutableStateFlow<EquipmentDetailState>(UiState.Loading)
        val equipment = _equipment.asStateFlow()

        init {
            loadEquipment()
        }

        fun loadEquipment() {
            viewModelScope.launch {
                try {
                    equipmentUseCase.getEquipmentByIdUseCase(equipmentId).collect { equipment ->
                        _equipment.value = UiState.Success(equipment)
                    }
                } catch (e: Exception) {
                    _equipment.value =
                        UiState.Error(
                            message = e.message ?: "Une erreur est survenue",
                        )
                }
            }
        }

        fun updateEquipment(equipment: com.example.beelditechtest.domain.model.Equipment) {
            viewModelScope.launch {
                try {
                    equipmentUseCase.updateEquipmentUseCase(equipment)
                    loadEquipment()
                } catch (e: Exception) {
                    _equipment.value =
                        UiState.Error(
                            message = e.message ?: "Une erreur est survenue lors de la mise à jour",
                        )
                }
            }
        }
    }

@AssistedFactory
interface EquipmentDetailViewModelFactory {
    fun create(id: Int): EquipmentDetailViewModel
}
