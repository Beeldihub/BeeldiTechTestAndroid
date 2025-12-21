package com.example.beelditechtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.usecase.EquipmentUseCase
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
class EquipmentDetailViewModel @AssistedInject constructor(
    @Assisted
    val equipmentId: Int,
    private val equipmentUseCase: EquipmentUseCase,
) : ViewModel() {
    private val _equipment = MutableStateFlow<Equipment?>(null)
    val equipment = _equipment.asStateFlow()

    init {

    }

    fun loadEquipment() {
        viewModelScope.launch {
            try {
                equipmentUseCase.getEquipmentByIdUseCase(equipmentId).collect { equipment ->
                    _equipment.value = equipment
                }
            } catch (e: Exception) {

            }
        }
    }
}

@AssistedFactory
interface EquipmentDetailViewModelFactory {
    fun create(id: Int): EquipmentDetailViewModel
}
