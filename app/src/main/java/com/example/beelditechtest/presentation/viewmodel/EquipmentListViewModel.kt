package com.example.beelditechtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beelditechtest.core.util.normalize
import com.example.beelditechtest.domain.usecase.EquipmentUseCase
import com.example.beelditechtest.presentation.state.EquipmentListState
import com.example.beelditechtest.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EquipmentListViewModel
    @Inject
    constructor(
        private val equipmentUseCase: EquipmentUseCase,
    ) : ViewModel() {
        private val _equipments = MutableStateFlow<EquipmentListState>(UiState.Loading)
        val equipments = _equipments.asStateFlow()

        private val _searchQuery = MutableStateFlow("")
        val searchQuery = _searchQuery.asStateFlow()

        val filteredEquipments =
            combine(_equipments, _searchQuery) { state, query ->
                when (state) {
                    is UiState.Success -> {
                        if (query.isBlank()) {
                            state.data
                        } else {
                            val normalizedQuery = query.normalize()
                            state.data.filter { equipment ->
                                (equipment.level ?: "").normalize().contains(normalizedQuery, ignoreCase = true) ||
                                    equipment.name.normalize().contains(normalizedQuery, ignoreCase = true) ||
                                    equipment.brand.normalize().contains(normalizedQuery, ignoreCase = true) ||
                                    equipment.model.normalize().contains(normalizedQuery, ignoreCase = true) ||
                                    equipment.serialNumber.normalize().contains(normalizedQuery, ignoreCase = true) ||
                                    equipment.local.normalize().contains(normalizedQuery, ignoreCase = true) ||
                                    equipment.type.toString().contains(normalizedQuery)
                            }
                        }
                    }

                    else -> {
                        emptyList()
                    }
                }
            }

        init {
            loadEquipments()
        }

        fun loadEquipments() {
            viewModelScope.launch {
                try {
                    equipmentUseCase.getAllEquipmentUseCase().collect { equipmentList ->
                        _equipments.value = UiState.Success(equipmentList)
                    }
                } catch (e: Exception) {
                    _equipments.value =
                        UiState.Error(
                            message = e.message ?: "Une erreur est survenue",
                        )
                }
            }
        }

        fun searchEquipments(query: String) {
            _searchQuery.value = query
        }
    }
