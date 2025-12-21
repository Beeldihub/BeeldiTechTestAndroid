package com.example.beelditechtest.presentation.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.beelditechtest.presentation.viewmodel.EquipmentDetailViewModel

@Composable
fun EquipmentDetailScreen(
    modifier: Modifier = Modifier,
    equipmentId: Int,
    viewModel: EquipmentDetailViewModel,
    onNavigateBack: () -> Unit,
) {
    val equipment by viewModel.equipment.collectAsStateWithLifecycle()

    Text("$equipmentId")
}
