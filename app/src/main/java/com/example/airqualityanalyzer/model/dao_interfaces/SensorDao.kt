package com.example.airqualityanalyzer.model.dao_interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.airqualityanalyzer.model.Sensor

@Dao
interface SensorDao {

    @Insert
    suspend fun addSensor(sensor: Sensor)

    @Delete
    suspend fun deleteSensor(sensor: Sensor)

    @Query("SELECT * FROM sensor WHERE StationId = :stationId")
    fun stationSensors(stationId: Int): List<Sensor>

}