package com.example.beelditechtest.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.presentation.screen.component.EquipmentItem


@Composable
fun EquipmentListScreen(
    modifier: Modifier = Modifier
) {
    val equipments = listOf(
        Equipment(
            id = 1,
            name = "Equipment 1",
            brand = "Brand 1",
            model = "Model 1",
            serialNumber = "Serial 1",
            local = "Local 1",
            level = "Level 1",
            type = 1
        ),
        Equipment(
            id = 1,
            name = "Equipment 1",
            brand = "Brand 1",
            model = "Model 1",
            serialNumber = "Serial 1",
            local = "Local 1",
            level = "Level 1",
            type = 1
        ),
        Equipment(
            id = 1,
            name = "Equipment 1",
            brand = "Brand 1",
            model = "Model 1",
            serialNumber = "Serial 1",
            local = "Local 1",
            level = "Level 1",
            type = 1
        )
    )

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surfaceContainerHigh) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(14.dp)
        ) {
            items(equipments) { equipment ->
                EquipmentItem(equipment = equipment)
            }
        }
    }
}

@Preview
@Composable
fun EquipmentListScreenPreview() {
    EquipmentListScreen()
}