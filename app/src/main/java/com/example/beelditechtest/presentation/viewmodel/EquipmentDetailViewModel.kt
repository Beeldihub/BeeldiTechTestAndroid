package com.example.beelditechtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beelditechtest.domain.usecase.EquipmentUseCase
import com.example.beelditechtest.domain.validation.EquipmentValidation
import com.example.beelditechtest.presentation.state.EquipmentDetailState
import com.example.beelditechtest.presentation.state.UiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
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

        private val _brand = MutableStateFlow("")
        val brand = _brand.asStateFlow()

        private val _model = MutableStateFlow("")
        val model = _model.asStateFlow()

        private val _serialNumber = MutableStateFlow("")
        val serialNumber = _serialNumber.asStateFlow()

        val brandError =
            _brand.map { brandValue ->
                if (brandValue.isNotEmpty()) {
                    EquipmentValidation.validateBrand(brandValue.trim())
                } else {
                    null
                }
            }

        val modelError =
            _model.map { modelValue ->
                if (modelValue.isNotEmpty()) {
                    EquipmentValidation.validateModel(modelValue.trim())
                } else {
                    null
                }
            }

        val serialNumberError =
            _serialNumber.map { serialValue ->
                if (serialValue.isNotEmpty()) {
                    EquipmentValidation.validateSerialNumber(serialValue.trim().uppercase())
                } else {
                    null
                }
            }

        val hasErrors =
            combine(brandError, modelError, serialNumberError) { brandErr, modelErr, serialErr ->
                brandErr != null || modelErr != null || serialErr != null
            }

        init {
            loadEquipment()
        }

        fun loadEquipment() {
            viewModelScope.launch {
                try {
                    equipmentUseCase.getEquipmentByIdUseCase(equipmentId).collect { equipment ->
                        _equipment.value = UiState.Success(equipment)
                        equipment?.let {
                            _brand.value = it.brand
                            _model.value = it.model
                            _serialNumber.value = it.serialNumber
                        }
                    }
                } catch (e: Exception) {
                    _equipment.value =
                        UiState.Error(
                            message = e.message ?: "Une erreur est survenue",
                        )
                }
            }
        }

        fun updateBrand(value: String) {
            _brand.value = value
        }

        fun updateModel(value: String) {
            _model.value = value
        }

        fun updateSerialNumber(value: String) {
            val filteredValue = value.replace(" ", "").uppercase()
            _serialNumber.value = filteredValue
        }

        fun saveEquipment() {
            viewModelScope.launch {
                val currentHasErrors = hasErrors.first()
                if (!currentHasErrors) {
                    val currentEquipment = (_equipment.value as? UiState.Success)?.data
                    currentEquipment?.let { equipment ->
                        try {
                            equipmentUseCase.updateEquipmentUseCase(
                                equipment.copy(
                                    brand = _brand.value.trim(),
                                    model = _model.value.trim(),
                                    serialNumber = _serialNumber.value.trim().uppercase(),
                                ),
                            )
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
        }
    }

@AssistedFactory
interface EquipmentDetailViewModelFactory {
    fun create(id: Int): EquipmentDetailViewModel
}
