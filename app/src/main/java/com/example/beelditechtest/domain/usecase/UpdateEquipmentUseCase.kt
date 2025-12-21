package com.example.beelditechtest.domain.usecase

import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.repository.EquipmentRepository
import javax.inject.Inject

class UpdateEquipmentUseCase
    @Inject
    constructor(
        private val repository: EquipmentRepository,
    ) {
        suspend operator fun invoke(equipment: Equipment) = repository.updateEquipment(equipment)
    }
