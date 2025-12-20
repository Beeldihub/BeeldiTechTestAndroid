package com.example.beelditechtest.data.di

import android.content.Context
import androidx.room.Room
import com.example.beelditechtest.data.db.AppDataBase
import com.example.beelditechtest.data.db.dao.EquipmentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDataBase =
        Room
            .databaseBuilder(
                context,
                AppDataBase::class.java,
                "equipment-db",
            ).build()

    @Provides
    fun provideEquipmentDao(database: AppDataBase): EquipmentDao = database.equipmentDao()
}
