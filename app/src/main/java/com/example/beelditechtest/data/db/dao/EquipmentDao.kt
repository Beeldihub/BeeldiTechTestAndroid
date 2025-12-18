package com.example.beelditechtest.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.beelditechtest.data.db.entities.EquipmentEntity

@Dao
interface EquipmentDao {
    @Query("SELECT * FROM equipment")
    suspend fun getAllEquipments(): List<EquipmentEntity>
}