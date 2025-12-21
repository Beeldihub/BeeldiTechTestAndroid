package com.example.beelditechtest.presentation.viewmodel

import com.example.beelditechtest.MainDispatcherRule
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.domain.usecase.EquipmentUseCase
import com.example.beelditechtest.presentation.state.EquipmentListState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.collections.emptyList
import kotlin.collections.listOf

@OptIn(ExperimentalCoroutinesApi::class)
class EquipmentListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val equipmentUseCase = mockk<EquipmentUseCase>()

    @Test
    fun `should start with Loading state`() =
        runTest {
            every { equipmentUseCase.getAllEquipmentUseCase() } returns emptyFlow()

            val viewModel = EquipmentListViewModel(equipmentUseCase)

            assertEquals(EquipmentListState.Loading, viewModel.equipments.value)
        }

    @Test
    fun `should call use case once on init`() =
        runTest {
            every { equipmentUseCase.getAllEquipmentUseCase() } returns emptyFlow()

            EquipmentListViewModel(equipmentUseCase)
            advanceUntilIdle()

            verify(exactly = 1) { equipmentUseCase.getAllEquipmentUseCase() }
        }

    @Test
    fun `should set Success when equipments are emitted`() =
        runTest {
            val equipments =
                listOf(
                    Equipment(1, "name", "brand", "model", "serialNumber", "local", "level", 1),
                    Equipment(2, "name", "brand", "model", "serialNumber", "local", "level", 1),
                )
            every { equipmentUseCase.getAllEquipmentUseCase() } returns flowOf(equipments)

            val vm = EquipmentListViewModel(equipmentUseCase)
            advanceUntilIdle()

            assertEquals(EquipmentListState.Success(equipments), vm.equipments.value)
        }

    @Test
    fun `should update Success on each emission`() =
        runTest {
            val flow = MutableSharedFlow<List<Equipment>>(replay = 0)
            every { equipmentUseCase.getAllEquipmentUseCase() } returns flow

            val viewModel = EquipmentListViewModel(equipmentUseCase)
            advanceUntilIdle()

            val list1 =
                listOf(
                    Equipment(1, "name", "brand", "model", "serialNumber", "local", "level", 1),
                    Equipment(2, "name", "brand", "model", "serialNumber", "local", "level", 1),
                )
            val list2 =
                listOf(
                    Equipment(1, "name", "brand", "model", "serialNumber", "local", "level", 1),
                    Equipment(2, "name", "brand", "model", "serialNumber", "local", "level", 1),
                    Equipment(3, "name", "brand", "model", "serialNumber", "local", "level", 1),
                )

            flow.emit(list1)
            advanceUntilIdle()
            assertEquals(EquipmentListState.Success(list1), viewModel.equipments.value)

            flow.emit(list2)
            advanceUntilIdle()
            assertEquals(EquipmentListState.Success(list2), viewModel.equipments.value)
        }

    @Test
    fun `should set Error when use case throws`() =
        runTest {
            every { equipmentUseCase.getAllEquipmentUseCase() } throws IllegalStateException("Error from use case")

            val viewModel = EquipmentListViewModel(equipmentUseCase)
            advanceUntilIdle()

            val state = viewModel.equipments.value
            check(state is EquipmentListState.Error)
            assertEquals("Error from use case", state.message)
        }

    @Test
    fun `should call use case again when loadEquipments is called`() =
        runTest {
            every { equipmentUseCase.getAllEquipmentUseCase() } returns flowOf(emptyList())
            val viewModel = EquipmentListViewModel(equipmentUseCase)
            advanceUntilIdle()

            viewModel.loadEquipments()
            advanceUntilIdle()

            verify(exactly = 2) { equipmentUseCase.getAllEquipmentUseCase() }
        }

    @Test
    fun `should return equipment by search query with title`() =
        runTest {
            val equipments =
                listOf(
                    Equipment(
                        1,
                        "Ventilation mécanique",
                        "Aldes",
                        "VMC Simple Flux",
                        "ALD-2021-078",
                        "Bâtiment C",
                        "Étage 3",
                        1,
                    ),
                    Equipment(
                        2,
                        "Chaudière",
                        "Viessmann",
                        "Vitodens 200-W",
                        "VIE-2023-123",
                        "Bâtiment A",
                        "Sous-sol",
                        2,
                    ),
                )
            every { equipmentUseCase.getAllEquipmentUseCase() } returns flowOf(equipments)

            val viewModel = EquipmentListViewModel(equipmentUseCase)
            advanceUntilIdle()

            viewModel.searchEquipments("Chaudière")
            advanceUntilIdle()
            assertEquals(listOf(equipments[1]), viewModel.filteredEquipments.first())

            // Search in lowercase and uppercase
            viewModel.searchEquipments("chaudiere")
            advanceUntilIdle()
            assertEquals(listOf(equipments[1]), viewModel.filteredEquipments.first())

            viewModel.searchEquipments("CHAUDIERE")
            advanceUntilIdle()
            assertEquals(listOf(equipments[1]), viewModel.filteredEquipments.first())

            viewModel.searchEquipments("test")
            advanceUntilIdle()
            assertEquals(emptyList<Equipment>(), viewModel.filteredEquipments.first())

            viewModel.searchEquipments("")
            advanceUntilIdle()
            assertEquals(equipments, viewModel.filteredEquipments.first())
        }

    @Test
    fun `should return equipment by search query with brand`() =
        runTest {
            val equipments =
                listOf(
                    Equipment(
                        1,
                        "Ventilation mécanique",
                        "Aldes",
                        "VMC Simple Flux",
                        "ALD-2021-078",
                        "Bâtiment C",
                        "Étage 3",
                        1,
                    ),
                    Equipment(
                        2,
                        "Chaudière",
                        "Viessmann",
                        "Vitodens 200-W",
                        "VIE-2023-123",
                        "Bâtiment A",
                        "Sous-sol",
                        2,
                    ),
                )
            every { equipmentUseCase.getAllEquipmentUseCase() } returns flowOf(equipments)

            val viewModel = EquipmentListViewModel(equipmentUseCase)
            advanceUntilIdle()

            viewModel.searchEquipments("Viessmann")
            advanceUntilIdle()
            assertEquals(listOf(equipments[1]), viewModel.filteredEquipments.first())

            // Search in lowercase and uppercase
            viewModel.searchEquipments("viessmann")
            advanceUntilIdle()
            assertEquals(listOf(equipments[1]), viewModel.filteredEquipments.first())

            viewModel.searchEquipments("VIESSMANN")
            advanceUntilIdle()
            assertEquals(listOf(equipments[1]), viewModel.filteredEquipments.first())
        }

    @Test
    fun `should return equipment by search query with model`() =
        runTest {
            val equipments =
                listOf(
                    Equipment(
                        1,
                        "Ventilation mécanique",
                        "Aldes",
                        "VMC Simple Flux",
                        "ALD-2021-078",
                        "Bâtiment C",
                        "Étage 3",
                        1,
                    ),
                    Equipment(
                        2,
                        "Chaudière",
                        "Viessmann",
                        "Vitodens 200-W",
                        "VIE-2023-123",
                        "Bâtiment A",
                        "Sous-sol",
                        2,
                    ),
                )
            every { equipmentUseCase.getAllEquipmentUseCase() } returns flowOf(equipments)

            val viewModel = EquipmentListViewModel(equipmentUseCase)
            advanceUntilIdle()

            viewModel.searchEquipments("Vitodens 200-W")
            advanceUntilIdle()
            assertEquals(listOf(equipments[1]), viewModel.filteredEquipments.first())

            // Search in lowercase and uppercase
            viewModel.searchEquipments("vitodens 200-w")
            advanceUntilIdle()
            assertEquals(listOf(equipments[1]), viewModel.filteredEquipments.first())

            viewModel.searchEquipments("VITODENS 200-W")
            advanceUntilIdle()
            assertEquals(listOf(equipments[1]), viewModel.filteredEquipments.first())
        }

    @Test
    fun `should return equipment by search query with serialNumber`() =
        runTest {
            val equipments =
                listOf(
                    Equipment(
                        1,
                        "Ventilation mécanique",
                        "Aldes",
                        "VMC Simple Flux",
                        "ALD-2021-078",
                        "Bâtiment C",
                        "Étage 3",
                        1,
                    ),
                    Equipment(
                        2,
                        "Chaudière",
                        "Viessmann",
                        "Vitodens 200-W",
                        "VIe-2023-123",
                        "Bâtiment A",
                        "Sous-sol",
                        2,
                    ),
                )
            every { equipmentUseCase.getAllEquipmentUseCase() } returns flowOf(equipments)

            val viewModel = EquipmentListViewModel(equipmentUseCase)
            advanceUntilIdle()

            viewModel.searchEquipments("VIe-2023-123")
            advanceUntilIdle()
            assertEquals(listOf(equipments[1]), viewModel.filteredEquipments.first())

            // Search in lowercase and uppercase
            viewModel.searchEquipments("vie-2023-123")
            advanceUntilIdle()
            assertEquals(listOf(equipments[1]), viewModel.filteredEquipments.first())

            viewModel.searchEquipments("VIE-2023-123")
            advanceUntilIdle()
            assertEquals(listOf(equipments[1]), viewModel.filteredEquipments.first())
        }

    @Test
    fun `should return equipment by search query with local`() =
        runTest {
            val equipments =
                listOf(
                    Equipment(
                        1,
                        "Ventilation mécanique",
                        "Aldes",
                        "VMC Simple Flux",
                        "ALD-2021-078",
                        "Bâtiment C",
                        "Étage 3",
                        1,
                    ),
                    Equipment(
                        2,
                        "Chaudière",
                        "Viessmann",
                        "Vitodens 200-W",
                        "VIE-2023-123",
                        "Bâtiment A",
                        "Sous-sol",
                        2,
                    ),
                )
            every { equipmentUseCase.getAllEquipmentUseCase() } returns flowOf(equipments)

            val viewModel = EquipmentListViewModel(equipmentUseCase)
            advanceUntilIdle()

            viewModel.searchEquipments("Bâtiment A")
            advanceUntilIdle()
            assertEquals(listOf(equipments[1]), viewModel.filteredEquipments.first())

            // Search in lowercase and uppercase
            viewModel.searchEquipments("batiment a")
            advanceUntilIdle()
            assertEquals(listOf(equipments[1]), viewModel.filteredEquipments.first())

            viewModel.searchEquipments("BATIMENT A")
            advanceUntilIdle()
            assertEquals(listOf(equipments[1]), viewModel.filteredEquipments.first())
        }

    @Test
    fun `should return equipment by search query with level`() =
        runTest {
            val equipments =
                listOf(
                    Equipment(
                        1,
                        "Ventilation mécanique",
                        "Aldes",
                        "VMC Simple Flux",
                        "ALD-2021-078",
                        "Bâtiment C",
                        "Étage 3",
                        1,
                    ),
                    Equipment(
                        2,
                        "Chaudière",
                        "Viessmann",
                        "Vitodens 200-W",
                        "VIE-2023-123",
                        "Bâtiment A",
                        "Sous-sol",
                        2,
                    ),
                )
            every { equipmentUseCase.getAllEquipmentUseCase() } returns flowOf(equipments)

            val viewModel = EquipmentListViewModel(equipmentUseCase)
            advanceUntilIdle()

            viewModel.searchEquipments("Sous-sol")
            advanceUntilIdle()
            assertEquals(listOf(equipments[1]), viewModel.filteredEquipments.first())

            // Search in lowercase and uppercase
            viewModel.searchEquipments("sous-sol")
            advanceUntilIdle()
            assertEquals(listOf(equipments[1]), viewModel.filteredEquipments.first())

            viewModel.searchEquipments("SOUS-SOL")
            advanceUntilIdle()
            assertEquals(listOf(equipments[1]), viewModel.filteredEquipments.first())
        }

    @Test
    fun `should return equipment by search query with type`() =
        runTest {
            val equipments =
                listOf(
                    Equipment(
                        1,
                        "Ventilation mécanique",
                        "Aldes",
                        "VMC Simple Flux",
                        "ALD-2021-078",
                        "Bâtiment C",
                        "Étage 3",
                        3,
                    ),
                    Equipment(
                        2,
                        "Chaudière",
                        "Viessmann",
                        "Vitodens 200-W",
                        "VIE-2023-123",
                        "Bâtiment A",
                        "Sous-sol",
                        4,
                    ),
                )
            every { equipmentUseCase.getAllEquipmentUseCase() } returns flowOf(equipments)

            val viewModel = EquipmentListViewModel(equipmentUseCase)
            advanceUntilIdle()

            viewModel.searchEquipments("4")
            advanceUntilIdle()
            assertEquals(listOf(equipments[1]), viewModel.filteredEquipments.first())
        }
}
