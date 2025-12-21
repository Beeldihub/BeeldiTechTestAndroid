package com.example.beelditechtest.data.mapper

import com.example.beelditechtest.data.db.entities.EquipmentEntity
import com.example.beelditechtest.domain.model.Equipment
import junit.framework.TestCase.assertEquals
import org.junit.Test

class EquipmentMapperTest {
    @Test
    fun `toDomain should map EquipmentEntity to Equipment`() {
        val equipmentEntity =
            EquipmentEntity(
                1,
                "name",
                "brand",
                "model",
                "serialNumber",
                "local",
                "level",
                1,
            )
        val equipment: Equipment = equipmentEntity.toDomain()

        assertEquals(equipmentEntity.id, equipment.id)
        assertEquals(equipmentEntity.name, equipment.name)
        assertEquals(equipmentEntity.brand, equipment.brand)
        assertEquals(equipmentEntity.model, equipment.model)
        assertEquals(equipmentEntity.serialNumber, equipment.serialNumber)
        assertEquals(equipmentEntity.local, equipment.local)
        assertEquals(equipmentEntity.level, equipment.level)
        assertEquals(equipmentEntity.type, equipment.type)
    }

    @Test
    fun `toEntity should map Equipment to EquipmentEntity`() {
        val equipment =
            Equipment(
                1,
                "name",
                "brand",
                "model",
                "serialNumber",
                "local",
                "level",
                1,
            )
        val equipmentEntity: EquipmentEntity = equipment.toEntity()

        assertEquals(equipment.id, equipmentEntity.id)
        assertEquals(equipment.name, equipmentEntity.name)
        assertEquals(equipment.brand, equipmentEntity.brand)
        assertEquals(equipment.model, equipmentEntity.model)
        assertEquals(equipment.serialNumber, equipmentEntity.serialNumber)
        assertEquals(equipment.local, equipmentEntity.local)
        assertEquals(equipment.level, equipmentEntity.level)
        assertEquals(equipment.type, equipmentEntity.type)
    }
}
