package com.example.beelditechtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beelditechtest.domain.usecase.EquipmentUseCase
import com.example.beelditechtest.presentation.state.EquipmentListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EquipmentListViewModel
    @Inject
    constructor(
        private val equipmentUseCase: EquipmentUseCase,
    ) : ViewModel() {
        private val _equipments = MutableStateFlow<EquipmentListState>(EquipmentListState.Loading)
        val equipments = _equipments.asStateFlow()

        init {
            loadEquipments()
        }

        fun loadEquipments() {
            viewModelScope.launch {
                try {
                    equipmentUseCase.getAllEquipmentUseCase().collect { equipmentList ->
                        _equipments.value = EquipmentListState.Success(equipmentList)
                    }
                } catch (e: Exception) {
                    _equipments.value =
                        EquipmentListState.Error(
                            message = e.message ?: "Une erreur est survenue",
                        )
                }
            }
        }
    }
