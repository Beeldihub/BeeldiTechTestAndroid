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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EquipmentListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val equipmentUseCase = mockk<EquipmentUseCase>()

    @Test
    fun `should start with Loading state`() = runTest {
        every { equipmentUseCase.getAllEquipmentUseCase() } returns emptyFlow()

        val viewModel = EquipmentListViewModel(equipmentUseCase)

        assertEquals(EquipmentListState.Loading, viewModel.equipments.value)
    }

    @Test
    fun `should call use case once on init`() = runTest {
        every { equipmentUseCase.getAllEquipmentUseCase() } returns emptyFlow()

        EquipmentListViewModel(equipmentUseCase)
        advanceUntilIdle()

        verify(exactly = 1) { equipmentUseCase.getAllEquipmentUseCase() }
    }

    @Test
    fun `should set Success when equipments are emitted`() = runTest {
        val equipments = listOf(
            Equipment(1, "name", "brand", "model", "serialNumber", "local", "level", 1),
            Equipment(2, "name", "brand", "model", "serialNumber", "local", "level", 1)
        )
        every { equipmentUseCase.getAllEquipmentUseCase() } returns flowOf(equipments)

        val vm = EquipmentListViewModel(equipmentUseCase)
        advanceUntilIdle()

        assertEquals(EquipmentListState.Success(equipments), vm.equipments.value)
    }

    @Test
    fun `should update Success on each emission`() = runTest {
        val flow = MutableSharedFlow<List<Equipment>>(replay = 0)
        every { equipmentUseCase.getAllEquipmentUseCase() } returns flow

        val viewModel = EquipmentListViewModel(equipmentUseCase)
        advanceUntilIdle()

        val list1 = listOf(
            Equipment(1, "name", "brand", "model", "serialNumber", "local", "level", 1),
            Equipment(2, "name", "brand", "model", "serialNumber", "local", "level", 1)
        )
        val list2 = listOf(
            Equipment(1, "name", "brand", "model", "serialNumber", "local", "level", 1),
            Equipment(2, "name", "brand", "model", "serialNumber", "local", "level", 1),
            Equipment(3, "name", "brand", "model", "serialNumber", "local", "level", 1)
        )

        flow.emit(list1)
        advanceUntilIdle()
        assertEquals(EquipmentListState.Success(list1), viewModel.equipments.value)

        flow.emit(list2)
        advanceUntilIdle()
        assertEquals(EquipmentListState.Success(list2), viewModel.equipments.value)
    }

    @Test
    fun `should set Error when use case throws`() = runTest {
        every { equipmentUseCase.getAllEquipmentUseCase() } throws IllegalStateException("Error from use case")

        val viewModel = EquipmentListViewModel(equipmentUseCase)
        advanceUntilIdle()

        val state = viewModel.equipments.value
        check(state is EquipmentListState.Error)
        assertEquals("Error from use case", state.message)
    }

    @Test
    fun `should call use case again when loadEquipments is called`() = runTest {
        every { equipmentUseCase.getAllEquipmentUseCase() } returns flowOf(emptyList())
        val vm = EquipmentListViewModel(equipmentUseCase)
        advanceUntilIdle()

        vm.loadEquipments()
        advanceUntilIdle()

        verify(exactly = 2) { equipmentUseCase.getAllEquipmentUseCase() }
    }
}