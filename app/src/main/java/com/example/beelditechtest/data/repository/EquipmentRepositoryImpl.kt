package com.example.beelditechtest.data.repository

import com.example.beelditechtest.data.datasources.EquipmentDataSource
import com.example.beelditechtest.domain.models.Equipment
import com.example.beelditechtest.domain.repository.EquipmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** Repository implementation backed by the local JSON data source. */


class EquipmentRepositoryImpl (
    private val dataSource: EquipmentDataSource
) : EquipmentRepository {
    override fun getEquipments(): Flow<List<Equipment>> {
    return dataSource.getEquipments()
        .map { entities ->
            entities.map { entity ->
                Equipment(
                    id = entity.id,
                    name = entity.name,
                    brand = entity.brand,
                    model = entity.model,
                    serialNumber = entity.serialNumber,
                    location = entity.location,
                    type = entity.type
                )
            }
        }
}
}