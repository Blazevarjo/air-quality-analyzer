package com.example.airqualityanalyzer.model.repositories

import androidx.lifecycle.LiveData
import com.example.airqualityanalyzer.model.entities.Sensor
import com.example.airqualityanalyzer.model.daoInterfaces.SensorDao

class SensorRepository(private val sensorDao: SensorDao) {

    fun stationSensors(stationId: Int): LiveData<List<Sensor>> {
        return sensorDao.stationSensors(stationId)
    }

    suspend fun addSensor(sensor: Sensor) {
        sensorDao.addSensor(sensor)
    }

    suspend fun deleteSensor(sensor: Sensor) {
        sensorDao.deleteSensor(sensor)
    }

}