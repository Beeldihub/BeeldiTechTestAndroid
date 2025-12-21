package com.example.beelditechtest.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.beelditechtest.presentation.ui.component.EquipmentItem
import com.example.beelditechtest.presentation.ui.component.SearchBar
import com.example.beelditechtest.presentation.state.EquipmentListState
import com.example.beelditechtest.presentation.viewmodel.EquipmentListViewModel

@Composable
fun EquipmentListScreen(
    modifier: Modifier = Modifier,
    viewModel: EquipmentListViewModel,
    onEquipmentClick: (Int) -> Unit = {},
) {
    val equipmentState = viewModel.equipments.collectAsStateWithLifecycle().value
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val filteredEquipments by viewModel.filteredEquipments.collectAsStateWithLifecycle(initialValue = emptyList())

    Surface(
        modifier =
            modifier
                .fillMaxSize(),
    ) {
        when (equipmentState) {
            is EquipmentListState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is EquipmentListState.Success -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    SearchBar(searchQuery, viewModel)

                    if (filteredEquipments.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text =
                                    if (searchQuery.isBlank()) {
                                        "Aucun équipement trouvé"
                                    } else {
                                        "Aucun équipement ne correspond à votre recherche"
                                    },
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(horizontal = 14.dp),
                        ) {
                            items(filteredEquipments) { equipment ->
                                EquipmentItem(
                                    equipment = equipment,
                                    onClick = { onEquipmentClick(equipment.id) },
                                )
                            }
                        }
                    }
                }
            }

            is EquipmentListState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Erreur: ${equipmentState.message}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        }
    }
}
