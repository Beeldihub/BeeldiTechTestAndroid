package com.example.beelditechtest.data.mapper
import com.example.beelditechtest.data.model.EquipmentEntity
import com.example.beelditechtest.domain.model.EquipmentUiModel

object EquipmentMapper {
    fun toUiModel(entity: EquipmentEntity): EquipmentUiModel {
        return EquipmentUiModel(
            id = entity.id,
            name = entity.name,
            brand = entity.brand,
            model = entity.model,
            serialNumber = entity.serialNumber,
            location = entity.location,
            type = entity.type
        )
    }


    fun toUiModelList(entities: List<EquipmentEntity>): List<EquipmentUiModel> {
        return entities.map { toUiModel(it) }
    }
}