package com.example.beelditechtest.data.repository
import com.example.beelditechtest.data.datasource.local.EquipmentLocalRepository
import com.example.beelditechtest.data.mapper.EquipmentMapper
import com.example.beelditechtest.data.model.EquipmentEntity
import com.example.beelditechtest.domain.model.EquipmentUiModel
import com.example.beelditechtest.domain.repository.EquipmentRepository

class EquipmentRepositoryImpl(
    private val localDataSource: EquipmentLocalRepository
) : EquipmentRepository {

    override suspend fun getEquipments(): Result<List<EquipmentEntity>> {
        return try {
            val entities = localDataSource.getEquipments()
            Result.success(entities)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}