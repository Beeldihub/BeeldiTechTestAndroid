package com.example.beelditechtest.presentation.equipment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp
import com.example.beelditechtest.domain.model.EquipmentUiModel

@Composable
fun EquipmentListLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun EquipmentListError(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Erreur: $message",
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun EquipmentListContent(
    equipmentUiModels: List<EquipmentUiModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(equipmentUiModels) { equipment ->
            EquipmentCard(
                equipmentUiModel = equipment,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun EquipmentListScreen(
    uiState: EquipmentScreenUiModel,
    onRetry: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    when (val currentState = uiState) {
        is EquipmentScreenUiModel.Loading -> {
            EquipmentListLoading(modifier = modifier)
        }
        is EquipmentScreenUiModel.Error -> {
            EquipmentListError(
                message = currentState.message,
                modifier = modifier
            )
        }
        is EquipmentScreenUiModel.Success -> {
            EquipmentListContent(
                equipmentUiModels = currentState.equipmentUiModels,
                modifier = modifier
            )
        }
    }
}