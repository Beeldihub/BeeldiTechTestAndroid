package com.example.beelditechtest.domain.usecase

import javax.inject.Inject

data class EquipmentUseCase
    @Inject
    constructor(
        val getAllEquipmentUseCase: GetAllEquipmentUseCase,
        val getEquipmentByIdUseCase: GetEquipmentById,
    )
