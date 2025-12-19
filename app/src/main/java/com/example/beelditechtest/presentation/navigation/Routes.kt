package com.example.beelditechtest.presentation.navigation

sealed class Screen(val route: String) {
    data object EquipmentList : Screen("equipment_list")
    data object EquipmentDetail : Screen("equipment_detail/{equipmentId}") {
        fun createRoute(equipmentId: Int) = "equipment_detail/$equipmentId"
    }
}