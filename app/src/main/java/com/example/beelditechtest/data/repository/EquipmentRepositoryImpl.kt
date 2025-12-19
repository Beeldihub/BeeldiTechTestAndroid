package com.example.beelditechtest.data.repository

import com.example.beelditechtest.data.db.dao.EquipmentDao
import com.example.beelditechtest.data.mapper.toDomain
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.repository.EquipmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EquipmentRepositoryImpl(
    private val equipmentDao: EquipmentDao
) : EquipmentRepository {
    override fun getAllEquipments(): Flow<List<Equipment>> =
        equipmentDao.getAllEquipments().map { list -> list.map { it.toDomain() } }
}