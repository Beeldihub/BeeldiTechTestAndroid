package com.example.beelditechtest.presentation.equipment.list
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
import com.example.beelditechtest.domain.model.Equipment

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
    equipments: List<Equipment>,
    onEquipmentClick: (Equipment) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(equipments) { equipment ->
            EquipmentCard(
                equipment = equipment,
                onClick = { onEquipmentClick(equipment) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun EquipmentListScreen(
    uiState: EquipmentListUiState,
    onRetry: () -> Unit = {},
    onEquipmentClick: (Equipment) -> Unit = {},
    modifier: Modifier = Modifier
) {
    when (val currentState = uiState) {
        is EquipmentListUiState.Loading -> {
            EquipmentListLoading(modifier = modifier)
        }
        is EquipmentListUiState.Error -> {
            EquipmentListError(
                message = currentState.message,
                modifier = modifier
            )
        }
        is EquipmentListUiState.Success -> {
            EquipmentListContent(
                equipments = currentState.equipments,
                onEquipmentClick = onEquipmentClick,
                modifier = modifier
            )
        }
    }
}