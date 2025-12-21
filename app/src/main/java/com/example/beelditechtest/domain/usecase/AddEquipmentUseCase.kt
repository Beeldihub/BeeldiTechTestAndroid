package com.example.beelditechtest.domain.usecase

import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.repository.EquipmentRepository
import javax.inject.Inject

class AddEquipmentUseCase
    @Inject
    constructor(
        private val equipmentRepository: EquipmentRepository,
    ) {
        suspend operator fun invoke(equipment: Equipment) = equipmentRepository.insertEquipment(equipment)
    }
