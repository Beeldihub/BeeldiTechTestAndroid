package com.example.beelditechtest.domain.repository


import com.example.beelditechtest.domain.models.Equipment
import kotlinx.coroutines.flow.Flow

/** Abstraction for retrieving equipment data. */
interface EquipmentRepository {
    fun getEquipments(): Flow<List<Equipment>>
}