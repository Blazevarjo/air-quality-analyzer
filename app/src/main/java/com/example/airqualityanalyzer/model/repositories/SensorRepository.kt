package com.example.airqualityanalyzer.model.repositories

import com.example.airqualityanalyzer.model.Sensor
import com.example.airqualityanalyzer.model.daoInterfaces.SensorDao

class SensorRepository(private val sensorDao: SensorDao) {

    fun stationSensors(stationId: Int): List<Sensor> {
        return sensorDao.stationSensors(stationId)
    }

    suspend fun addSensor(sensor: Sensor) {
        sensorDao.addSensor(sensor)
    }

    suspend fun deleteSensor(sensor: Sensor) {
        sensorDao.deleteSensor(sensor)
    }

}