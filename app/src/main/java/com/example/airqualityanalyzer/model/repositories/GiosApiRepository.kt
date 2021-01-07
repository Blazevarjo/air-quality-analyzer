package com.example.airqualityanalyzer.model.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.airqualityanalyzer.model.entities.Data
import com.example.airqualityanalyzer.model.entities.Sensor
import com.example.airqualityanalyzer.model.entities.Station
import com.example.airqualityanalyzer.model.api.GIOSService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GiosApiRepository {
    companion object {
        fun getAllStations(): MutableLiveData<List<Station>> {
            val stations = MutableLiveData<List<Station>>()

            val call = GIOSService.api.allStations()
            call.enqueue(object : Callback<List<Station>> {
                override fun onResponse(
                    call: Call<List<Station>>,
                    response: Response<List<Station>>
                ) {
                    if (response.isSuccessful) {
                        val responseStations = response.body()
                        if (responseStations != null) {
                            stations.postValue(responseStations)
                        } else {
                            Log.e("AllStations Response", "Null response")
                        }

                    } else {
                        TODO("Not yet implemented")
                    }
                }

                override fun onFailure(call: Call<List<Station>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
            return stations
        }


        suspend fun getStationSensorsById(stationId: Int): List<Sensor> =
            suspendCoroutine { continuation ->
                val call = GIOSService.api.stationSensorsById(stationId)
                call.enqueue(object : Callback<List<Sensor>> {
                    override fun onResponse(
                        call: Call<List<Sensor>>,
                        response: Response<List<Sensor>>
                    ) {
                        if (response.isSuccessful) {
                            val sensors: List<Sensor>? = response.body()
                            if (sensors != null) {
                                continuation.resume(sensors)
                            }
                        } else {
                            TODO("Not yet implemented")
                        }
                    }

                    override fun onFailure(call: Call<List<Sensor>>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }

        suspend fun getSensorDataByIdAsync(sensorId: Int): Data =
            suspendCoroutine { continuation ->
                val call = GIOSService.api.sensorDataById(sensorId)
                call.enqueue(object : Callback<Data> {
                    override fun onResponse(
                        call: Call<Data>,
                        response: Response<Data>
                    ) {
                        if (response.isSuccessful) {
                            val data: Data? = response.body()
                            if (data != null) {
                                data.values = data.values.filter {
                                    it.value != null
                                }
                                continuation.resume(data)
                            }
                        } else {
                            TODO("Not yet implemented")
                        }
                    }

                    override fun onFailure(call: Call<Data>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }

        fun getSensorDataByIdSync(sensorId: Int): Data? {
            try {
                val call = GIOSService.api.sensorDataById(sensorId)
                val response = call.execute()

                if (response.isSuccessful) {
                    val data: Data? = response.body()
                    if (data != null) {
                        data.values = data.values.filter {
                            it.value != null
                        }
                        return data
                    }
                }
                return null
            } catch (error: IOException) {
                Log.e("Api data sensors", error.message ?: "")
                return null
            }
        }
    }
}