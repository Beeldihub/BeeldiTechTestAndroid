package com.example.beelditechtest.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.beelditechtest.domain.models.Equipment

/** Displays detailed information for a single equipment. */
@Composable
fun EquipmentDetailScreen(
    equipment: Equipment,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = equipment.name,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(8.dp))

        Text("Marque : ${equipment.brand}")
        Text("Modèle : ${equipment.model}")
        Text("Numéro de série : ${equipment.serialNumber}")
        Text("Localisation : ${equipment.location}")
        Text("Type : ${equipment.type}")
    }
}
