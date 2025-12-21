package com.example.beelditechtest.domain.usecase

import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.repository.EquipmentRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UpdateEquipmentUseCaseTest {
    private val repository = mockk<EquipmentRepository>()
    private val useCase = UpdateEquipmentUseCase(repository)

    @Test
    fun `should call repository updateEquipment when invoked`() =
        runTest {
            val equipment =
                Equipment(
                    id = 1,
                    name = "Test Equipment",
                    brand = "Apple",
                    model = "iPhone 15",
                    serialNumber = "ABC-123",
                    local = "Bâtiment A",
                    level = "Étage 1",
                    type = 1,
                )
            coEvery { repository.updateEquipment(equipment) } returns Unit

            useCase(equipment)

            coVerify(exactly = 1) { repository.updateEquipment(equipment) }
        }

    @Test
    fun `should update equipment with new brand`() =
        runTest {
            val originalEquipment =
                Equipment(
                    id = 1,
                    name = "Test Equipment",
                    brand = "Apple",
                    model = "iPhone 15",
                    serialNumber = "ABC-123",
                    local = "Bâtiment A",
                    level = "Étage 1",
                    type = 1,
                )
            val updatedEquipment = originalEquipment.copy(brand = "Samsung")
            coEvery { repository.updateEquipment(updatedEquipment) } returns Unit

            useCase(updatedEquipment)

            coVerify(exactly = 1) { repository.updateEquipment(updatedEquipment) }
        }

    @Test
    fun `should update equipment with new model`() =
        runTest {
            val originalEquipment =
                Equipment(
                    id = 1,
                    name = "Test Equipment",
                    brand = "Apple",
                    model = "iPhone 15",
                    serialNumber = "ABC-123",
                    local = "Bâtiment A",
                    level = "Étage 1",
                    type = 1,
                )
            val updatedEquipment = originalEquipment.copy(model = "Galaxy S24")
            coEvery { repository.updateEquipment(updatedEquipment) } returns Unit

            useCase(updatedEquipment)

            coVerify(exactly = 1) { repository.updateEquipment(updatedEquipment) }
        }

    @Test
    fun `should update equipment with new serial number`() =
        runTest {
            val originalEquipment =
                Equipment(
                    id = 1,
                    name = "Test Equipment",
                    brand = "Apple",
                    model = "iPhone 15",
                    serialNumber = "ABC-123",
                    local = "Bâtiment A",
                    level = "Étage 1",
                    type = 1,
                )
            val updatedEquipment = originalEquipment.copy(serialNumber = "XYZ-789")
            coEvery { repository.updateEquipment(updatedEquipment) } returns Unit

            useCase(updatedEquipment)

            coVerify(exactly = 1) { repository.updateEquipment(updatedEquipment) }
        }

    @Test
    fun `should update equipment with multiple fields changed`() =
        runTest {
            val originalEquipment =
                Equipment(
                    id = 1,
                    name = "Test Equipment",
                    brand = "Apple",
                    model = "iPhone 15",
                    serialNumber = "ABC-123",
                    local = "Bâtiment A",
                    level = "Étage 1",
                    type = 1,
                )
            val updatedEquipment =
                originalEquipment.copy(
                    brand = "Samsung",
                    model = "Galaxy S24",
                    serialNumber = "XYZ-789",
                )
            coEvery { repository.updateEquipment(updatedEquipment) } returns Unit

            useCase(updatedEquipment)

            coVerify(exactly = 1) { repository.updateEquipment(updatedEquipment) }
        }
}
