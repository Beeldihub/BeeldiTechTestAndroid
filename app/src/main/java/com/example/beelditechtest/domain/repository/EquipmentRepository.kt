package com.example.beelditechtest.domain.repository
import com.example.beelditechtest.domain.model.Equipment

interface EquipmentRepository {
    suspend fun getEquipments(): Result<List<Equipment>>
}