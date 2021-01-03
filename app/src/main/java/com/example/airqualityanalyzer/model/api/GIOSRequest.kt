package com.example.airqualityanalyzer.model.api

import com.example.airqualityanalyzer.model.Data
import com.example.airqualityanalyzer.model.Sensor
import com.example.airqualityanalyzer.model.Station
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GIOSRequest {

    @GET("station/findAll")
    fun allStations(): Call<List<Station>>

    @GET("station/sensors/{stationId}")
    fun stationSensors(@Path("stationId") stationId: Int): Call<List<Sensor>>

    @GET("data/getData/{sensorId}")
    fun sensorData(@Path("sensorId") sensorId: Int): Call<Data>

}