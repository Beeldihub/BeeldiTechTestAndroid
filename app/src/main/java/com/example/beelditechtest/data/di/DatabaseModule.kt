package com.example.beelditechtest.data.di

import android.content.Context
import androidx.room.Room
import com.example.beelditechtest.data.db.AppDataBase

object DatabaseModule {
    fun provide(context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "equipment-db"
        ).build()
    }
}