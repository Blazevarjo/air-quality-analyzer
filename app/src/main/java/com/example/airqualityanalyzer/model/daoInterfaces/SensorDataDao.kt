package com.example.airqualityanalyzer.model.daoInterfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.airqualityanalyzer.model.entities.SensorData
import java.util.*

@Dao
interface SensorDataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSensorData(sensorData: SensorData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addSensorDataSync(sensorData: SensorData)

    @Delete
    suspend fun deleteSensorData(sensorData: SensorData)

    @Query("SELECT * FROM sensor_data WHERE id = :sensorId AND date > :begin AND date < :end")
    fun sensorDataBetween(sensorId: Int, begin: Date, end: Date): LiveData<List<SensorData>>

}