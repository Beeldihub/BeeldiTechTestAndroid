package com.example.beelditechtest.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.presentation.screen.component.EquipmentItem
import com.example.beelditechtest.presentation.state.EquipmentListState
import com.example.beelditechtest.presentation.viewmodel.EquipmentListViewModel

@Composable
fun EquipmentListScreen(
    viewModel: EquipmentListViewModel,
    onEquipmentClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val state = viewModel.equipments.collectAsStateWithLifecycle().value

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceContainerHigh
    ) {
        when (state) {
            is EquipmentListState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is EquipmentListState.Success -> {
                if (state.equipments.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Aucun équipement trouvé",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(14.dp)
                    ) {
                        items(state.equipments) { equipment ->
                            EquipmentItem(
                                equipment = equipment,
                                onClick = { onEquipmentClick(equipment.id) }
                            )
                        }
                    }
                }
            }
            is EquipmentListState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Erreur: ${state.message}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun EquipmentListScreenPreview() {
    // Preview sans ViewModel pour l'instant
}