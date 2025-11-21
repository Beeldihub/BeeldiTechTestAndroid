package com.example.beelditechtest.domain.repository
import com.example.beelditechtest.data.model.EquipmentEntity
import com.example.beelditechtest.domain.model.EquipmentUiModel

interface EquipmentRepository {
    suspend fun getEquipments(): Result<List<EquipmentEntity>>
}