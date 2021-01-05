package com.example.airqualityanalyzer.model.api

import com.example.airqualityanalyzer.model.entities.Data
import com.example.airqualityanalyzer.model.entities.Sensor
import com.example.airqualityanalyzer.model.entities.Station
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GIOSRequest {

    @GET("station/findAll")
    fun allStations(): Call<List<Station>>

    @GET("station/sensors/{stationId}")
    fun stationSensorsById(@Path("stationId") stationId: Int): Call<List<Sensor>>

    @GET("data/getData/{sensorId}")
    fun sensorDataById(@Path("sensorId") sensorId: Int): Call<Data>

}