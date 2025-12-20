package com.example.beelditechtest.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("equipment")
data class EquipmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val brand: String,
    val model: String,
    val serialNumber: String,
    val local: String,
    val level: String?,
    val type: Int,
)
