package com.example.beelditechtest.presentation.viewmodel

import app.cash.turbine.test
import com.example.beelditechtest.MainDispatcherRule
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.usecase.EquipmentUseCase
import com.example.beelditechtest.presentation.state.UiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EquipmentDetailViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val equipmentUseCase = mockk<EquipmentUseCase>()
    private val equipmentId = 1

    private fun createViewModel(): EquipmentDetailViewModel = EquipmentDetailViewModel(equipmentId, equipmentUseCase)

    @Test
    fun `should start with Loading state`() =
        runTest {
            every { equipmentUseCase.getEquipmentByIdUseCase(equipmentId) } returns flowOf(null)

            val viewModel = createViewModel()

            assertEquals(UiState.Loading, viewModel.equipment.value)
        }

    @Test
    fun `should load equipment and initialize form fields`() =
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
            every { equipmentUseCase.getEquipmentByIdUseCase(equipmentId) } returns flowOf(equipment)

            val viewModel = createViewModel()
            advanceUntilIdle()

            assertEquals(UiState.Success(equipment), viewModel.equipment.value)
            assertEquals("Apple", viewModel.brand.value)
            assertEquals("iPhone 15", viewModel.model.value)
            assertEquals("ABC-123", viewModel.serialNumber.value)
        }

    @Test
    fun `should update brand when updateBrand is called`() =
        runTest {
            val equipment =
                Equipment(
                    id = 1,
                    name = "Test",
                    brand = "Apple",
                    model = "iPhone",
                    serialNumber = "ABC123",
                    local = "Local",
                    level = null,
                    type = 1,
                )
            every { equipmentUseCase.getEquipmentByIdUseCase(equipmentId) } returns flowOf(equipment)

            val viewModel = createViewModel()
            advanceUntilIdle()

            viewModel.updateBrand("Samsung")
            advanceUntilIdle()

            assertEquals("Samsung", viewModel.brand.value)
        }

    @Test
    fun `should update model when updateModel is called`() =
        runTest {
            val equipment =
                Equipment(
                    id = 1,
                    name = "Test",
                    brand = "Apple",
                    model = "iPhone",
                    serialNumber = "ABC123",
                    local = "Local",
                    level = null,
                    type = 1,
                )
            every { equipmentUseCase.getEquipmentByIdUseCase(equipmentId) } returns flowOf(equipment)

            val viewModel = createViewModel()
            advanceUntilIdle()

            viewModel.updateModel("Galaxy S24")
            advanceUntilIdle()

            assertEquals("Galaxy S24", viewModel.model.value)
        }

    @Test
    fun `should update serial number and convert to uppercase when updateSerialNumber is called`() =
        runTest {
            val equipment =
                Equipment(
                    id = 1,
                    name = "Test",
                    brand = "Apple",
                    model = "iPhone",
                    serialNumber = "ABC123",
                    local = "Local",
                    level = null,
                    type = 1,
                )
            every { equipmentUseCase.getEquipmentByIdUseCase(equipmentId) } returns flowOf(equipment)

            val viewModel = createViewModel()
            advanceUntilIdle()

            viewModel.updateSerialNumber("xyz-789")
            advanceUntilIdle()

            assertEquals("XYZ-789", viewModel.serialNumber.value)
        }

    @Test
    fun `should remove spaces from serial number when updateSerialNumber is called`() =
        runTest {
            val equipment =
                Equipment(
                    id = 1,
                    name = "Test",
                    brand = "Apple",
                    model = "iPhone",
                    serialNumber = "ABC123",
                    local = "Local",
                    level = null,
                    type = 1,
                )
            every { equipmentUseCase.getEquipmentByIdUseCase(equipmentId) } returns flowOf(equipment)

            val viewModel = createViewModel()
            advanceUntilIdle()

            viewModel.updateSerialNumber("abc 123")
            advanceUntilIdle()

            assertEquals("ABC123", viewModel.serialNumber.value)
        }

    @Test
    fun `should validate brand and return error when invalid`() =
        runTest {
            val equipment =
                Equipment(
                    id = 1,
                    name = "Test",
                    brand = "Apple",
                    model = "iPhone",
                    serialNumber = "ABC123",
                    local = "Local",
                    level = null,
                    type = 1,
                )
            every { equipmentUseCase.getEquipmentByIdUseCase(equipmentId) } returns flowOf(equipment)

            val viewModel = createViewModel()
            advanceUntilIdle()

            viewModel.updateBrand("apple")
            advanceUntilIdle()

            viewModel.brandError.test {
                val error = awaitItem()
                assertEquals("La marque doit commencer par une majuscule", error)
            }
        }

    @Test
    fun `should validate brand and return null when valid`() =
        runTest {
            val equipment =
                Equipment(
                    id = 1,
                    name = "Test",
                    brand = "Apple",
                    model = "iPhone",
                    serialNumber = "ABC123",
                    local = "Local",
                    level = null,
                    type = 1,
                )
            every { equipmentUseCase.getEquipmentByIdUseCase(equipmentId) } returns flowOf(equipment)

            val viewModel = createViewModel()
            advanceUntilIdle()

            viewModel.updateBrand("Samsung")
            advanceUntilIdle()

            viewModel.brandError.test {
                val error = awaitItem()
                assertNull(error)
            }
        }

    @Test
    fun `should validate model and return error when exceeds 50 characters`() =
        runTest {
            val equipment =
                Equipment(
                    id = 1,
                    name = "Test",
                    brand = "Apple",
                    model = "iPhone",
                    serialNumber = "ABC123",
                    local = "Local",
                    level = null,
                    type = 1,
                )
            every { equipmentUseCase.getEquipmentByIdUseCase(equipmentId) } returns flowOf(equipment)

            val viewModel = createViewModel()
            advanceUntilIdle()

            viewModel.updateModel("M".repeat(51))
            advanceUntilIdle()

            viewModel.modelError.test {
                val error = awaitItem()
                assertEquals("Le modèle ne doit pas dépasser 50 caractères", error)
            }
        }

    @Test
    fun `should not save equipment when there are validation errors`() =
        runTest {
            val equipment =
                Equipment(
                    id = 1,
                    name = "Test",
                    brand = "Apple",
                    model = "iPhone",
                    serialNumber = "ABC123",
                    local = "Local",
                    level = null,
                    type = 1,
                )
            every { equipmentUseCase.getEquipmentByIdUseCase(equipmentId) } returns flowOf(equipment)
            coEvery { equipmentUseCase.updateEquipmentUseCase(any()) } returns Unit

            val viewModel = createViewModel()
            advanceUntilIdle()

            viewModel.updateBrand("invalid")
            advanceUntilIdle()

            viewModel.saveEquipment()
            advanceUntilIdle()

            coVerify(exactly = 0) { equipmentUseCase.updateEquipmentUseCase(any()) }
        }

    @Test
    fun `should save equipment when validation passes`() =
        runTest {
            val equipment =
                Equipment(
                    id = 1,
                    name = "Test",
                    brand = "Apple",
                    model = "iPhone",
                    serialNumber = "ABC123",
                    local = "Local",
                    level = null,
                    type = 1,
                )
            val flow = MutableSharedFlow<Equipment?>(replay = 1)
            flow.emit(equipment)
            every { equipmentUseCase.getEquipmentByIdUseCase(equipmentId) } returns flow
            coEvery { equipmentUseCase.updateEquipmentUseCase(any()) } returns Unit

            val viewModel = createViewModel()
            advanceUntilIdle()

            viewModel.updateBrand("Samsung")
            viewModel.updateModel("Galaxy S24")
            viewModel.updateSerialNumber("XYZ-789")
            advanceUntilIdle()

            viewModel.saveEquipment()
            advanceUntilIdle()

            coVerify(exactly = 1) {
                equipmentUseCase.updateEquipmentUseCase(
                    equipment.copy(
                        brand = "Samsung",
                        model = "Galaxy S24",
                        serialNumber = "XYZ-789",
                    ),
                )
            }
        }

    @Test
    fun `should trim and uppercase serial number when saving`() =
        runTest {
            val equipment =
                Equipment(
                    id = 1,
                    name = "Test",
                    brand = "Apple",
                    model = "iPhone",
                    serialNumber = "ABC123",
                    local = "Local",
                    level = null,
                    type = 1,
                )
            val flow = MutableSharedFlow<Equipment?>(replay = 1)
            flow.emit(equipment)
            every { equipmentUseCase.getEquipmentByIdUseCase(equipmentId) } returns flow
            coEvery { equipmentUseCase.updateEquipmentUseCase(any()) } returns Unit

            val viewModel = createViewModel()
            advanceUntilIdle()

            viewModel.updateBrand("Samsung")
            viewModel.updateModel("Galaxy S24")
            viewModel.updateSerialNumber("  xyz-789  ")
            advanceUntilIdle()

            viewModel.saveEquipment()
            advanceUntilIdle()

            coVerify(exactly = 1) {
                equipmentUseCase.updateEquipmentUseCase(
                    equipment.copy(
                        brand = "Samsung",
                        model = "Galaxy S24",
                        serialNumber = "XYZ-789",
                    ),
                )
            }
        }

    @Test
    fun `should set Error state when update fails`() =
        runTest {
            val equipment =
                Equipment(
                    id = 1,
                    name = "Test",
                    brand = "Apple",
                    model = "iPhone",
                    serialNumber = "ABC123",
                    local = "Local",
                    level = null,
                    type = 1,
                )
            val flow = MutableSharedFlow<Equipment?>(replay = 1)
            flow.emit(equipment)
            every { equipmentUseCase.getEquipmentByIdUseCase(equipmentId) } returns flow
            coEvery { equipmentUseCase.updateEquipmentUseCase(any()) } throws Exception("Update failed")

            val viewModel = createViewModel()
            advanceUntilIdle()

            viewModel.updateBrand("Samsung")
            viewModel.updateModel("Galaxy S24")
            viewModel.updateSerialNumber("XYZ-789")
            advanceUntilIdle()

            viewModel.saveEquipment()
            advanceUntilIdle()

            val state = viewModel.equipment.value
            assertEquals(UiState.Error("Update failed"), state)
        }

    @Test
    fun `should set Error state when loadEquipment fails`() =
        runTest {
            every { equipmentUseCase.getEquipmentByIdUseCase(equipmentId) } throws Exception("Load failed")

            val viewModel = createViewModel()
            advanceUntilIdle()

            val state = viewModel.equipment.value
            assertEquals(UiState.Error("Load failed"), state)
        }

    @Test
    fun `should return true for hasErrors when brand has error`() =
        runTest {
            val equipment =
                Equipment(
                    id = 1,
                    name = "Test",
                    brand = "Apple",
                    model = "iPhone",
                    serialNumber = "ABC123",
                    local = "Local",
                    level = null,
                    type = 1,
                )
            every { equipmentUseCase.getEquipmentByIdUseCase(equipmentId) } returns flowOf(equipment)

            val viewModel = createViewModel()
            advanceUntilIdle()

            viewModel.updateBrand("invalid")
            advanceUntilIdle()

            viewModel.hasErrors.test {
                val hasErrors = awaitItem()
                assertEquals(true, hasErrors)
            }
        }

    @Test
    fun `should return false for hasErrors when all fields are valid`() =
        runTest {
            val equipment =
                Equipment(
                    id = 1,
                    name = "Test",
                    brand = "Apple",
                    model = "iPhone",
                    serialNumber = "ABC123",
                    local = "Local",
                    level = null,
                    type = 1,
                )
            every { equipmentUseCase.getEquipmentByIdUseCase(equipmentId) } returns flowOf(equipment)

            val viewModel = createViewModel()
            advanceUntilIdle()

            viewModel.updateBrand("Samsung")
            viewModel.updateModel("Galaxy S24")
            viewModel.updateSerialNumber("XYZ-789")
            advanceUntilIdle()

            viewModel.hasErrors.test {
                val hasErrors = awaitItem()
                assertEquals(false, hasErrors)
            }
        }
}
