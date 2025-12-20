package com.example.beelditechtest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.beelditechtest.data.db.dao.EquipmentDao
import com.example.beelditechtest.data.db.entities.EquipmentEntity

@Database(
    entities = [EquipmentEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun equipmentDao(): EquipmentDao
}
