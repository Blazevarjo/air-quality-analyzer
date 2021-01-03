package com.example.airqualityanalyzer.model.repositories

import androidx.lifecycle.LiveData
import com.example.airqualityanalyzer.model.SensorData
import com.example.airqualityanalyzer.model.daoInterfaces.SensorDataDao
import java.util.*

class SensorDataRepository(private val sensorDataDao: SensorDataDao) {

    fun sensorData(sensorId: Int, begin: Date, end: Date ): LiveData<List<SensorData>> {
        return sensorDataDao.sensorDataBetween(sensorId, begin, end)
    }

    suspend fun addSensorData(sensorData: SensorData) {
        sensorDataDao.addSensorData(sensorData)
    }

    suspend fun deleteSensorData(sensorData: SensorData) {
        sensorDataDao.deleteSensorData(sensorData)
    }

}