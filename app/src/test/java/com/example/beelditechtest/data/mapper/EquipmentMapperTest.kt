package com.example.beelditechtest.data.mapper

import com.example.beelditechtest.data.db.entities.EquipmentEntity
import com.example.beelditechtest.domain.model.Equipment
import org.junit.Test
import junit.framework.TestCase.assertEquals

class EquipmentMapperTest {
    @Test
    fun `toDomain should map EquipmentEntity to Equipment`() {
        val equipmentEntity = EquipmentEntity(
            1,
            "name",
            "brand",
            "model",
            "serialNumber",
            "local",
            "level",
            1
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
}