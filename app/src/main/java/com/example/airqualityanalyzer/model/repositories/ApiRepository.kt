package com.example.airqualityanalyzer.model.repositories

import androidx.lifecycle.MutableLiveData
import com.example.airqualityanalyzer.model.entities.Data
import com.example.airqualityanalyzer.model.entities.Sensor
import com.example.airqualityanalyzer.model.entities.Station
import com.example.airqualityanalyzer.model.api.GIOSService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class ApiRepository {
    companion object {
        fun getAllStations(): MutableLiveData<List<Station>> {
//            GIOSService.api.allStations().awaitResponse().body() ?: emptyList()
            val stations = MutableLiveData<List<Station>>()

            val call = GIOSService.api.allStations()
            call.enqueue(object : Callback<List<Station>> {
                override fun onResponse(
                    call: Call<List<Station>>,
                    response: Response<List<Station>>
                ) {
                    if (response.isSuccessful) {

                        stations.postValue(response.body())
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


        fun getStationSensorsById(stationId: Int): MutableLiveData<List<Sensor>> {
            val sensors = MutableLiveData<List<Sensor>>()

            val call = GIOSService.api.stationSensorsById(stationId)
            call.enqueue(object : Callback<List<Sensor>> {
                override fun onResponse(
                    call: Call<List<Sensor>>,
                    response: Response<List<Sensor>>
                ) {
                    if (response.isSuccessful) {
                        sensors.postValue(response.body())
                    } else {
                        TODO("Not yet implemented")
                    }
                }

                override fun onFailure(call: Call<List<Sensor>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
            return sensors
        }

        fun getSensorDataById(sensorId: Int): MutableLiveData<Data> {
            val data = MutableLiveData<Data>()

            val call = GIOSService.api.sensorDataById(sensorId)
            call.enqueue(object : Callback<Data> {
                override fun onResponse(call: Call<Data>, response: Response<Data>) {
                    if (response.isSuccessful) {
                        data.postValue(response.body())
                    } else {
                        TODO("Not yet implemented")
                    }
                }

                override fun onFailure(call: Call<Data>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
            return data
        }
    }
}