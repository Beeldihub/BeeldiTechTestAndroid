package com.example.beelditechtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.beelditechtest.data.datasources.EquipmentDataSource
import com.example.beelditechtest.data.repository.EquipmentRepositoryImpl
import com.example.beelditechtest.domain.usecases.GetEquipmentsUseCase
import com.example.beelditechtest.presentation.list.EquipmentListScreen
import com.example.beelditechtest.presentation.list.EquipmentListViewModel
import com.example.beelditechtest.ui.theme.BeeldiTechTestTheme
import com.example.beelditechtest.ui.theme.screenBackground
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.beelditechtest.presentation.detail.EquipmentDetailScreen
import com.example.beelditechtest.domain.models.Equipment


/** Sets up dependencies and hosts the Compose UI. */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔗 Wiring manuel des dépendances (simple pour un test)
        val dataSource = EquipmentDataSource(context = this)
        val repository = EquipmentRepositoryImpl(dataSource)
        val getEquipmentsUseCase = GetEquipmentsUseCase(repository)
        val viewModel = EquipmentListViewModel(getEquipmentsUseCase)

        enableEdgeToEdge()
        setContent {
            BeeldiTechTestTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(screenBackground)
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "list",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("list") {
                            EquipmentListScreen(
                                viewModel = viewModel,
                                onEquipmentClick = { equipment: Equipment ->
                                    navController.navigate("detail/${equipment.id}")
                                }
                            )
                        }

                        composable("detail/{equipmentId}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("equipmentId")
                            val equipment = id?.let { viewModel.getEquipmentById(it) }

                            if (equipment != null) {
                                EquipmentDetailScreen(equipment = equipment)
                            } else {
                                // Fallback simple si l'id est inconnu
                                androidx.compose.material3.Text("Équipement introuvable")
                            }
                        }
                    }
                }
            }
        }
    }
}
