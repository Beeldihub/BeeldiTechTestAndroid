package com.example.beelditechtest.data.datasources

import android.content.Context
import com.example.beelditechtest.data.models.EquipmentEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONArray
import java.io.IOException

/** Loads equipment data from the local JSON asset. */

class EquipmentDataSource(private val context: Context) {

    fun getEquipments(): Flow<List<EquipmentEntity>> = flow {
        try {
            val jsonString = context.assets.open("equipments.json")
                .bufferedReader()
                .use { it.readText() }

            val jsonArray = JSONArray(jsonString)
            val equipments = mutableListOf<EquipmentEntity>()

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)

                val typeAsString = jsonObject.getString("type")
                val type = typeAsString.toIntOrNull() ?: 0

                val equipment = EquipmentEntity(
                    id = jsonObject.getString("id"),
                    name = jsonObject.getString("name"),
                    brand = jsonObject.getString("brand"),
                    model = jsonObject.getString("model"),
                    serialNumber = jsonObject.getString("serialNumber"),
                    location = jsonObject.getString("location"),
                    type = type
                )
                equipments.add(equipment)
            }

            emit(equipments)
        } catch (e: IOException) {
            emit(emptyList())
        } catch (e: Exception) {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)
}
