package com.example.beelditechtest.domain.usecase

import app.cash.turbine.test
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.repository.EquipmentRepository
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotEquals
import org.junit.Test

class GetAllEquipmentUseCaseTest {
    private val repository = mockk<EquipmentRepository>()
    private val useCase = GetAllEquipmentUseCase(repository)

    @Test
    fun `return all equipments when repository contains equipments`() =
        runTest {
            val expected =
                listOf(
                    Equipment(1, "name", "brand", "model", "serialNumber", "local", "level", 1),
                    Equipment(2, "name", "brand", "model", "serialNumber", "local", "level", 1),
                )
            val notExpected =
                listOf(
                    Equipment(3, "name", "brand", "model", "serialNumber", "local", "level", 1),
                )
            val flow = flowOf(expected)
            every { repository.getAllEquipments() } returns flow

            useCase().test {
                val result = awaitItem()
                assertEquals(expected, result)
                assertNotEquals(notExpected, result)
                awaitComplete()
            }
        }

    @Test
    fun `return empty list when repository is empty`() =
        runTest {
            val expected = emptyList<Equipment>()
            val flow = flowOf(expected)
            every { repository.getAllEquipments() } returns flow

            useCase().test {
                val result = awaitItem()
                assertEquals(expected, result)
                awaitComplete()
            }
        }
}
