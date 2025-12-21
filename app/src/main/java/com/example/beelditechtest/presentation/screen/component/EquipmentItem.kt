package com.example.beelditechtest.presentation.screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.beelditechtest.domain.model.Equipment

@Composable
fun EquipmentItem(
    modifier: Modifier = Modifier,
    equipment: Equipment,
    onClick: () -> Unit = {},
) {
    Surface(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
    ) {
        Row(
            modifier = Modifier.padding(18.dp, 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = equipment.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Text(text = "${equipment.brand} - ${equipment.model}")
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    text = "Série: ${equipment.serialNumber}",
                    style = MaterialTheme.typography.labelSmall,
                )
                Text(
                    text = equipment.local,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Red,
                )
            }
        }
    }
}

@Preview
@Composable
fun EquipmentItemPreview() {
    EquipmentItem(
        equipment =
            Equipment(
                id = 1,
                name = "Equipment 1",
                brand = "Brand 1",
                model = "Model 1",
                serialNumber = "Serial 1",
                local = "Local 1",
                level = "Level 1",
                type = 1,
            ),
    )
}
