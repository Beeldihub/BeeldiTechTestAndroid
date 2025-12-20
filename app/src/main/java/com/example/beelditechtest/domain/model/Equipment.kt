package com.example.beelditechtest.domain.model

data class Equipment(
    val id: Int,
    val name: String,
    val brand: String,
    val model: String,
    val serialNumber: String,
    val local: String,
    val level: String?,
    val type: Int,
)
