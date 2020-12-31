package com.example.airqualityanalyzer.model.repositories

import androidx.lifecycle.MutableLiveData
import com.example.airqualityanalyzer.model.Sensor
import com.example.airqualityanalyzer.model.dao_interfaces.SensorDao

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