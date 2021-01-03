package com.example.airqualityanalyzer.model.daoInterfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.airqualityanalyzer.model.SensorData
import java.util.*

@Dao
interface SensorDataDao {

    @Insert
    suspend fun addSensorData(sensorData: SensorData)

    @Delete
    suspend fun deleteSensorData(sensorData: SensorData)

    @Query("SELECT * FROM sensor_data WHERE id = :sensorId AND date > :begin AND date < :end")
    fun sensorDataBetween(sensorId: Int, begin: Date, end: Date): LiveData<List<SensorData>>

}