package com.example.airqualityanalyzer.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.airqualityanalyzer.model.AppDatabase
import com.example.airqualityanalyzer.model.entities.SensorData
import com.example.airqualityanalyzer.model.entities.Station
import com.example.airqualityanalyzer.model.repositories.GiosApiRepository
import com.example.airqualityanalyzer.model.repositories.SensorDataRepository
import com.example.airqualityanalyzer.model.repositories.SensorRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SensorDataViewModel(application: Application) : AndroidViewModel(application) {
    private val sensorRepository =
        SensorRepository(AppDatabase.getDatabase(application).sensorDao())
    private val sensorDataRepository =
        SensorDataRepository(AppDatabase.getDatabase(application).sensorDataDao())

    fun addAllSensorDataByStationId(station: Station): Job =
        viewModelScope.launch {
            Log.e("start", "sensordata")
            val sensors = sensorRepository.stationSensors(station.id)
            for (sensor in sensors) {
                val data = GiosApiRepository.getSensorDataByIdAsync(sensor.id)

                val valuesList = data.values

                for (values in valuesList) {
                    val sensorData = SensorData(0, sensor.id, values.date, values.value!!)
                    sensorDataRepository.addSensorData(sensorData)
                }
            }
            Log.e("finish", "sensordata")
        }
}