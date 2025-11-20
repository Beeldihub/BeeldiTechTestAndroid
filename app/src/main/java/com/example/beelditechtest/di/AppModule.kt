package com.example.beelditechtest.di
import android.content.Context
import com.example.beelditechtest.data.datasource.local.EquipmentLocalDataSource
import com.example.beelditechtest.data.repository.EquipmentRepositoryImpl
import com.example.beelditechtest.domain.repository.EquipmentRepository
import com.example.beelditechtest.domain.usecase.GetEquipmentsUseCase

object AppModule {
    fun provideEquipmentLocalDataSource(context: Context): EquipmentLocalDataSource {
        return EquipmentLocalDataSource(context)
    }

    fun provideEquipmentRepository(
        localDataSource: EquipmentLocalDataSource
    ): EquipmentRepository {
        return EquipmentRepositoryImpl(localDataSource)
    }

    fun provideGetEquipmentsUseCase(
        repository: EquipmentRepository
    ): GetEquipmentsUseCase {
        return GetEquipmentsUseCase(repository)
    }
}