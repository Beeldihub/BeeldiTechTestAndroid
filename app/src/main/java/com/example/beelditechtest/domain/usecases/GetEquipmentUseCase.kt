package com.example.beelditechtest.domain.usecases

import com.example.beelditechtest.domain.models.Equipment
import com.example.beelditechtest.domain.repository.EquipmentRepository
import kotlinx.coroutines.flow.Flow

/** Exposes equipment retrieval logic to the presentation layer. */

class GetEquipmentsUseCase(
    private val repository: EquipmentRepository
) {
    operator fun invoke(): Flow<List<Equipment>> = repository.getEquipments()
}