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

                try {
                    val id = jsonObject.optString("id") ?: continue
                    val name = jsonObject.optString("name", "Unnamed equipment")
                    val brand = jsonObject.optString("brand", "")
                    val model = jsonObject.optString("model", "")
                    val serialNumber = jsonObject.optString("serialNumber", "")
                    val location = jsonObject.optString("location", "")
                    val typeAsString = jsonObject.optString("type", "0")
                    val type = typeAsString.toIntOrNull() ?: 0

                    val equipment = EquipmentEntity(
                        id = id,
                        name = name,
                        brand = brand,
                        model = model,
                        serialNumber = serialNumber,
                        location = location,
                        type = type
                    )
                    equipments.add(equipment)
                } catch (_: Exception) {
                    // When there's an error, we ignore it to continue to show
                    continue
                }
            }

            emit(equipments)
        } catch (e: IOException) {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)
}
