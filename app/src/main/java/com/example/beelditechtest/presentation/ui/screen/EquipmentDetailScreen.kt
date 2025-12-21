package com.example.beelditechtest.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import com.example.beelditechtest.domain.model.Equipment
import com.example.beelditechtest.presentation.state.UiState
import com.example.beelditechtest.presentation.ui.component.TopBar
import com.example.beelditechtest.presentation.viewmodel.EquipmentDetailViewModel

@Composable
fun EquipmentDetailScreen(
    modifier: Modifier = Modifier,
    equipmentId: Int,
    viewModel: EquipmentDetailViewModel,
    onNavigateBack: () -> Unit,
) {
    val equipmentState by viewModel.equipment.collectAsStateWithLifecycle()
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isWide =
        windowSizeClass.isWidthAtLeastBreakpoint(
            WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND,
        )

    Surface(modifier = modifier) {
        when (equipmentState) {
            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                val equipment = (equipmentState as UiState.Success).data

                if (equipment == null) {
                    TopBar("Retour", onBackClick = onNavigateBack)
                    Text(text = "Aucun équipement trouvé avec l'ID $equipmentId")
                } else {
                    Column {
                        TopBar(equipment.name, onBackClick = onNavigateBack)

                        if (isWide) {
                            Row(
                                modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
                                ScreenContentLeft(
                                    equipment = equipment,
                                    modifier = Modifier.weight(1f),
                                )
                                ScreenContentRight(
                                    equipment = equipment,
                                    modifier = Modifier.weight(1f),
                                )
                            }
                        } else {
                            Column(
                                modifier = Modifier.fillMaxSize().padding(24.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
                                ScreenContentLeft(equipment, Modifier.fillMaxWidth())
                                ScreenContentRight(equipment, Modifier.fillMaxWidth())
                            }
                        }
                    }
                }
            }

            is UiState.Error -> {
                TopBar("Retour", onBackClick = onNavigateBack)
                Text(text = "Erreur: ${(equipmentState as UiState.Error).message}")
            }
        }
    }
}

@Composable
fun ScreenContentLeft(equipment: Equipment, modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLowest),
    ) {
        Box(
            Modifier
                .background(Color(0xFFFFC657))
                .padding(14.dp)
                .fillMaxWidth(),
        ) {
            Text(
                "Matériel",
                style = MaterialTheme.typography.titleLarge,
            )
        }
        HorizontalDivider()
        Row(
            modifier =
                Modifier
                    .padding(14.dp)
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("Marque", style = MaterialTheme.typography.titleMedium)
            Text(equipment.brand)
        }
        HorizontalDivider()
        Row(
            modifier =
                Modifier
                    .padding(14.dp)
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("Modèle", style = MaterialTheme.typography.titleMedium)
            Text(equipment.model)
        }
        HorizontalDivider()
        Row(
            modifier =
                Modifier
                    .padding(14.dp)
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("N° de série", style = MaterialTheme.typography.titleMedium)
            Text(equipment.serialNumber)
        }
    }
}

@Composable
fun ScreenContentRight(equipment: Equipment, modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLowest),
    ) {
        Box(
            Modifier
                .background(Color(0xFFFFC657))
                .padding(14.dp)
                .fillMaxWidth(),
        ) {
            Text(
                "Localisation",
                style = MaterialTheme.typography.titleLarge,
            )
        }
        HorizontalDivider()
        Row(
            modifier =
                Modifier
                    .padding(14.dp)
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            equipment.level?.let {
                Text("Niveau", style = MaterialTheme.typography.titleMedium)
                Text(it)
            }
        }
        HorizontalDivider()
        Row(
            modifier =
                Modifier
                    .padding(14.dp)
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("Local", style = MaterialTheme.typography.titleMedium)
            Text(equipment.local)
        }
    }
}
