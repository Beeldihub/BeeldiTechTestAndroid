package com.example.beelditechtest.data.repository

import app.cash.turbine.test
import com.example.beelditechtest.data.db.dao.EquipmentDao
import com.example.beelditechtest.data.db.entities.EquipmentEntity
import com.example.beelditechtest.domain.model.Equipment
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import junit.framework.TestCase.assertEquals

class EquipmentRepositoryImplTest {
    private val equipmentDao = mockk<EquipmentDao>()
    private val equipmentRepository = EquipmentRepositoryImpl(equipmentDao)

    @Test
    fun `getAllEquipments should return list of equipment domain`() = runTest {
        val equipmentEntities = listOf(
            EquipmentEntity(1, "name", "brand", "model", "serialNumber", "local", "level", 1),
            EquipmentEntity(2, "name", "brand", "model", "serialNumber", "local", "level", 1)
        )
        val expectedEquipments = listOf(
            Equipment(1, "name", "brand", "model", "serialNumber", "local", "level", 1),
            Equipment(2, "name", "brand", "model", "serialNumber", "local", "level", 1)
        )
        val flowEquipmentEntities = flowOf(equipmentEntities)
        every { equipmentDao.getAllEquipments() } returns flowEquipmentEntities

        equipmentRepository.getAllEquipments().test {
            val result = awaitItem()
            assertEquals(expectedEquipments, result)
            awaitComplete()
        }
    }
}