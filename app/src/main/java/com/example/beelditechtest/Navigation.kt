package com.example.beelditechtest

sealed class Screen(val route: String) {
    object EquipmentList : Screen("equipment_list")
    object EquipmentDetail : Screen("equipment_detail/{equipmentId}") {
        fun createRoute(equipmentId: String) = "equipment_detail/$equipmentId"
    }
}

