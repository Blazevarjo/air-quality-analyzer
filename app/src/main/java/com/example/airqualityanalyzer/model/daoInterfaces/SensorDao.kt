package com.example.airqualityanalyzer.model.daoInterfaces

import androidx.lifecycle.LiveData
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

    @Query("SELECT * FROM sensor WHERE stationId = :stationId")
    fun stationSensors(stationId: Int): LiveData<List<Sensor>>

}