package com.example.beelditechtest.data.di

import com.example.beelditechtest.data.repository.EquipmentRepositoryImpl
import com.example.beelditechtest.domain.repository.EquipmentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindEquipmentRepository(
        equipmentRepositoryImpl: EquipmentRepositoryImpl
    ): EquipmentRepository
}