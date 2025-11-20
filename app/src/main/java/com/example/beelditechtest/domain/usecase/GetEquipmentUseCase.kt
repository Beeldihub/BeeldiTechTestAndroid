package com.example.beelditechtest.domain.usecase
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.repository.EquipmentRepository

class GetEquipmentsUseCase(
    private val repository: EquipmentRepository
) {
    suspend operator fun invoke(): Result<List<Equipment>> {
        return repository.getEquipments()
    }
}