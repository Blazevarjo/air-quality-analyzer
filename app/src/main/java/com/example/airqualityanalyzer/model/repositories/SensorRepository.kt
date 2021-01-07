package com.example.airqualityanalyzer.model.repositories

import com.example.airqualityanalyzer.model.entities.Sensor
import com.example.airqualityanalyzer.model.daoInterfaces.SensorDao

class SensorRepository(private val sensorDao: SensorDao) {

    fun stationSensorsSync(): List<Sensor> = sensorDao.stationSensorsSync()

    suspend fun stationSensorsAsync() = sensorDao.stationSensorsAsync()


    suspend fun addSensor(sensor: Sensor) {
        sensorDao.addSensor(sensor)
    }

    suspend fun deleteSensor(sensor: Sensor) {
        sensorDao.deleteSensor(sensor)
    }

}