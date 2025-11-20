package com.example.beelditechtest.data.mapper
import com.example.beelditechtest.data.model.EquipmentEntity
import com.example.beelditechtest.domain.model.Equipment

object EquipmentMapper {
    fun toDomain(entity: EquipmentEntity): Equipment {
        return Equipment(
            id = entity.id,
            name = entity.name,
            brand = entity.brand,
            model = entity.model,
            serialNumber = entity.serialNumber,
            location = entity.location,
            type = entity.type
        )
    }


    fun toDomainList(entities: List<EquipmentEntity>): List<Equipment> {
        return entities.map { toDomain(it) }
    }


    fun toData(equipment: Equipment): EquipmentEntity {
        return EquipmentEntity(
            id = equipment.id,
            name = equipment.name,
            brand = equipment.brand,
            model = equipment.model,
            serialNumber = equipment.serialNumber,
            location = equipment.location,
            type = equipment.type
        )
    }


    fun toDataList(equipments: List<Equipment>): List<EquipmentEntity> {
        return equipments.map { toData(it) }
    }
}