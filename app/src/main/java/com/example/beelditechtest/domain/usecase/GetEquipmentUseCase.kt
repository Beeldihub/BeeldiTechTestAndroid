package com.example.beelditechtest.domain.usecase
import com.example.beelditechtest.data.mapper.EquipmentMapper
import com.example.beelditechtest.data.mapper.EquipmentMapper.toUiModel
import com.example.beelditechtest.data.mapper.EquipmentMapper.toUiModelList
import com.example.beelditechtest.domain.model.EquipmentUiModel
import com.example.beelditechtest.domain.repository.EquipmentRepository

class GetEquipmentsUseCase(
    private val repository: EquipmentRepository
) {
    suspend operator fun invoke(): Result<List<EquipmentUiModel>> {
        return repository.getEquipments().map {toUiModelList(entities = it)}
    }
}