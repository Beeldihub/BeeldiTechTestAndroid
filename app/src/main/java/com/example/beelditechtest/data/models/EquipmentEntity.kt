package com.example.beelditechtest.data.models

/** Raw equipment model exactly as stored in the JSON source. */

data class EquipmentEntity(
    val id: String,
    val name: String,
    val brand: String,
    val model: String,
    val serialNumber: String,
    val location: String,
    val type: Int,
)