package com.example.airqualityanalyzer.model.repositories

import com.example.airqualityanalyzer.model.SensorData
import com.example.airqualityanalyzer.model.daoInterfaces.SensorDataDao
import java.util.*

class SensorDataRepository(private val sensorDataDao: SensorDataDao) {

    fun sensorData(sensorId: Int, begin: Date, end: Date ) {
        sensorDataDao.sensorDataBetween(sensorId, begin, end)
    }

    suspend fun addSensorData(sensorData: SensorData) {
        sensorDataDao.addSensorData(sensorData)
    }

    suspend fun deleteSensorData(sensorData: SensorData) {
        sensorDataDao.deleteSensorData(sensorData)
    }

}