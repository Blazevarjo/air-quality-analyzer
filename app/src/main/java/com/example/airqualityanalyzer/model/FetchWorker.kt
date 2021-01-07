package com.example.airqualityanalyzer.model

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.airqualityanalyzer.model.entities.SensorData
import com.example.airqualityanalyzer.model.repositories.GiosApiRepository
import com.example.airqualityanalyzer.model.repositories.SensorDataRepository
import com.example.airqualityanalyzer.model.repositories.SensorRepository

class FetchWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        Log.i("FetchWorker", "Doing work")

        val sensorRepository =
            SensorRepository(AppDatabase.getDatabase(applicationContext).sensorDao())
        val sensorDataRepository =
            SensorDataRepository(AppDatabase.getDatabase(applicationContext).sensorDataDao())

        val sensors = sensorRepository.stationSensorsSync()

        for (sensor in sensors) {
            val data = GiosApiRepository.getSensorDataByIdSync(sensor.id)

            if (data != null) {
                val valuesList = data.values

                for (values in valuesList) {
                    val sensorData = SensorData(0, sensor.id, values.date, values.value!!)
                    Log.d("sensorData", sensorData.toString())
                    sensorDataRepository.addSensorDataSync(sensorData)
                }
            } else {
                return Result.failure()
            }
        }

        return Result.success()
    }
}