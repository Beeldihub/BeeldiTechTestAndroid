package com.example.beelditechtest.presentation.main
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.beelditechtest.di.AppModule
import com.example.beelditechtest.di.EquipmentViewModelFactory
import com.example.beelditechtest.presentation.equipment.EquipmentListScreen
import com.example.beelditechtest.presentation.equipment.EquipmentListViewModel
import com.example.beelditechtest.presentation.theme.BeeldiTechTestTheme
import com.example.beelditechtest.presentation.theme.screenBackground

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<EquipmentListViewModel> {
        // Création de la chaîne de dépendances via AppModule
        val dataSource = AppModule.provideEquipmentLocalDataSource(this@MainActivity)
        val repository = AppModule.provideEquipmentRepository(dataSource)
        val useCase = AppModule.provideGetEquipmentsUseCase(repository)

        // Injection du UseCase dans le ViewModel via la Factory
        EquipmentViewModelFactory(useCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BeeldiTechTestTheme {
                val uiState by viewModel.state.collectAsState()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(screenBackground)
                ) { innerPadding ->
                    EquipmentListScreen(
                        uiState = uiState,
                        onRetry = { viewModel.loadEquipments() },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}