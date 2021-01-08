package com.example.airqualityanalyzer.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.airqualityanalyzer.model.AppDatabase
import com.example.airqualityanalyzer.model.entities.Sensor
import com.example.airqualityanalyzer.model.entities.SensorData
import com.example.airqualityanalyzer.model.entities.Station
import com.example.airqualityanalyzer.model.repositories.SensorDataRepository
import com.example.airqualityanalyzer.model.repositories.SensorRepository
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

class GraphViewModel(application: Application) : AndroidViewModel(application) {

    private val sensorRepository: SensorRepository =
        SensorRepository(AppDatabase.getDatabase(application).sensorDao())

    private val sensorDataRepository: SensorDataRepository =
        SensorDataRepository(AppDatabase.getDatabase(application).sensorDataDao())

    var station: Station? = null
        set(value) {
            field = value
            viewModelScope.launch {
                stationSensors.value = sensorRepository.stationSensors(station!!.id)
            }
        }

    var stationSensors = MutableLiveData<List<Sensor>>()

    var selectedSensor = MutableLiveData<Sensor>()

    var sensorData = MutableLiveData<List<SensorData>>()


    var dateBegin = MutableLiveData<Date>()

    var dateEnd = MutableLiveData<Date>()

    var max = MutableLiveData<Double>()
    var min = MutableLiveData<Double>()
    var std = MutableLiveData<Double>()
    var avg = MutableLiveData<Double>()

    init {
        dateBegin.value = Date(0)
        dateEnd.value = Date()
    }

    fun setSelectedSensorToDefault() {
        selectedSensor.value = stationSensors.value?.get(0)
    }

    fun setSelectedSensorByParamCode(paramCode: String) {
        selectedSensor.value = stationSensors.value?.find {
            it.param.paramCode == paramCode
        }!!
    }

    fun updateSensorData() {
        if(selectedSensor.value != null) {
            viewModelScope.launch {
                sensorData.value = sensorDataRepository.sensorData(selectedSensor.value!!.id, dateBegin.value!!, dateEnd.value!!)
                Log.v("debug", selectedSensor.value.toString())
                Log.v("debug", sensorData.value.toString())
            }
        }
    }

    fun updateProperties() {
        updateMax()
        updateMin()
        updateStd()
        updateAvg()
    }

    private fun updateMax() {
        if(sensorData.value != null) {
            var tmpMax = Double.MIN_VALUE

            sensorData.value!!.forEach {
                if (it.value > tmpMax) {
                    tmpMax = it.value
                }
            }

            max.value = tmpMax
        }
        else {
            max.value = Double.NaN
        }
    }

    private fun updateMin() {
        if(sensorData.value != null) {
            var tmpMin = Double.MAX_VALUE

            sensorData.value!!.forEach {
                if (it.value < tmpMin) {
                    tmpMin = it.value
                }
            }

            min.value = tmpMin
        }
        else {
            min.value = Double.NaN
        }
    }

    private fun updateStd() {
        if(sensorData.value != null) {
            var avg = 0.0
            var tmpStd = 0.0

            sensorData.value!!.forEach {
                avg += it.value
            }
            avg /= sensorData.value!!.size.toDouble()

            sensorData.value!!.forEach {
                tmpStd += (it.value - avg).pow(2)
            }
            tmpStd /= sensorData.value!!.size.toDouble()

            std.value = sqrt(tmpStd)
        }
        else {
            std.value = Double.NaN
        }
    }

    private fun updateAvg() {
        if(sensorData.value != null) {
            var sum = 0.0

            sensorData.value!!.forEach {
                sum += it.value
            }

            if (sensorData.value!!.isNotEmpty()) {
                avg.value = sum / (sensorData.value!!.size.toDouble())
            } else {
                avg.value = Double.NaN
            }
        }
        else {
            avg.value = Double.NaN
        }
    }

}