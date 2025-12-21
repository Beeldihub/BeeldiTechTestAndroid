package com.example.beelditechtest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.beelditechtest.presentation.ui.screen.EquipmentDetailScreen
import com.example.beelditechtest.presentation.ui.screen.EquipmentListScreen
import com.example.beelditechtest.presentation.viewmodel.EquipmentDetailViewModel
import com.example.beelditechtest.presentation.viewmodel.EquipmentDetailViewModelFactory
import com.example.beelditechtest.presentation.viewmodel.EquipmentListViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.EquipmentList.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(route = Screen.EquipmentList.route) {
            val viewModel: EquipmentListViewModel = hiltViewModel()
            EquipmentListScreen(
                viewModel = viewModel,
                onEquipmentClick = { equipmentId ->
                    navController.navigate(Screen.EquipmentDetail.createRoute(equipmentId))
                },
            )
        }

        composable(
            route = Screen.EquipmentDetail.route,
            arguments =
                listOf(
                    navArgument("equipmentId") {
                        type = androidx.navigation.NavType.IntType
                    },
                ),
        ) { backStackEntry ->
            val equipmentId = backStackEntry.arguments?.getInt("equipmentId") ?: 0
            val viewModel = hiltViewModel<EquipmentDetailViewModel, EquipmentDetailViewModelFactory> {
                factory -> factory.create(equipmentId)
            }
            EquipmentDetailScreen(
                viewModel = viewModel,
                equipmentId = equipmentId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
