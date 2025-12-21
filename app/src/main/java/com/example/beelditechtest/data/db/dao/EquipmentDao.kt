package com.example.beelditechtest.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.beelditechtest.data.db.entities.EquipmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipmentDao {
    @Query("SELECT * FROM equipment")
    fun getAllEquipments(): Flow<List<EquipmentEntity>>

    @Query("SELECT * FROM equipment WHERE id = :id")
    fun getEquipmentById(id: Int): Flow<EquipmentEntity>

    @Update
    suspend fun updateEquipment(equipment: EquipmentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEquipment(equipment: EquipmentEntity)
}
