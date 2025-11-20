package com.example.beelditechtest.data.repository
import com.example.beelditechtest.data.datasource.local.EquipmentLocalDataSource
import com.example.beelditechtest.data.mapper.EquipmentMapper
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.repository.EquipmentRepository

class EquipmentRepositoryImpl(
    private val localDataSource: EquipmentLocalDataSource
) : EquipmentRepository {

    override suspend fun getEquipments(): Result<List<Equipment>> {
        return try {
            val entities = localDataSource.getEquipments()
            val equipments = EquipmentMapper.toDomainList(entities)
            Result.success(equipments)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}