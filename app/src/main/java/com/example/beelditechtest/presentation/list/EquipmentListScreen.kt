package com.example.beelditechtest.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.beelditechtest.UserRole
import com.example.beelditechtest.domain.models.Equipment

/** Displays the equipment list based on the current UI state. */

@Composable
fun EquipmentListScreen(
    viewModel: EquipmentListViewModel,
    onEquipmentClick: (Equipment) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()

    when {
        state.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Erreur: ${state.error}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        else -> {
            if (state.equipments.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Aucun équipement disponible.")
                }
            } else { // Add Error handling
                UserRoleSelector(
                    selected = viewModel.selectedRole.collectAsState().value,
                    onRoleSelected = { viewModel.setRole(it) }
                )

                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(state.equipments) { equipment ->
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
        }
    }
}
/**
 * Card displaying a single equipment item.
 *
 * Pure UI: does not contain any business or data logic.
 */

@Composable
fun EquipmentCard(
    equipment: Equipment,
    onClick: (Equipment) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { onClick(equipment) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = equipment.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${equipment.brand} - ${equipment.model}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = "Série: ${equipment.serialNumber}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = equipment.location,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "Type: ${equipment.type}",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
@Composable
fun UserRoleSelector(
    selected: UserRole,
    onRoleSelected: (UserRole) -> Unit
) {
    var expanded = remember { mutableStateOf(false) }

    Box {
        Text(
            text = "Role: ${selected.name}",
            modifier = Modifier
                .clickable { expanded.value = true }
                .padding(8.dp)
        )

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            UserRole.values().forEach { role ->
                DropdownMenuItem(
                    text = { Text(role.name) },
                    onClick = {
                        expanded.value = false
                        onRoleSelected(role)
                    }
                )
            }
        }
    }
}

