package com.example.airqualityanalyzer.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.airqualityanalyzer.model.AppDatabase
import com.example.airqualityanalyzer.model.entities.Station
import com.example.airqualityanalyzer.model.repositories.GiosApiRepository
import com.example.airqualityanalyzer.model.repositories.SensorRepository
import kotlinx.coroutines.launch

class SensorViewModel(application: Application) : AndroidViewModel(application) {
    private val sensorRepository: SensorRepository =
        SensorRepository(AppDatabase.getDatabase(application).sensorDao())

    fun addSensorsByStation(station: Station) {
        viewModelScope.launch {
            val sensors = GiosApiRepository.getStationSensorsById(station.id)
            for (sensor in sensors) {
                sensorRepository.addSensor(sensor)
            }
        }
    }
}