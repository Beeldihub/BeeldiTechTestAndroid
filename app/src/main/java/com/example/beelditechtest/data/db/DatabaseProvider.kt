package com.example.beelditechtest.data.db

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    fun provide(context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "equipment-db"
        ).build()
    }
}