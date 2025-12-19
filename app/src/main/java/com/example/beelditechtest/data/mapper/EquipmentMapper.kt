package com.example.beelditechtest.data.mapper

import com.example.beelditechtest.data.db.entities.EquipmentEntity
import com.example.beelditechtest.domain.model.Equipment

fun EquipmentEntity.toDomain() = Equipment(
    id = id,
    name = name,
    brand = brand,
    model = model,
    serialNumber = serialNumber,
    local = local,
    level = level,
    type = type
)