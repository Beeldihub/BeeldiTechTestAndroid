package com.example.beelditechtest.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.beelditechtest.data.db.entities.EquipmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipmentDao {
    @Query("SELECT * FROM equipment")
    fun getAllEquipments(): Flow<List<EquipmentEntity>>

    @Query("SELECT * FROM equipment WHERE id = :id")
    fun getEquipmentById(id: Int): Flow<EquipmentEntity>
}
