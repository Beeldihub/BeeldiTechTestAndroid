package com.example.beelditechtest.domain.usecase

import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.repository.EquipmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllEquipmentUseCase
    @Inject
    constructor(
        private val repository: EquipmentRepository,
    ) {
        operator fun invoke(): Flow<List<Equipment>> = repository.getAllEquipments()
    }
