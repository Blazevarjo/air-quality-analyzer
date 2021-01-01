package com.example.airqualityanalyzer.model.repositories

import com.example.airqualityanalyzer.model.Station
import com.example.airqualityanalyzer.model.daoInterfaces.StationDao

class StationRepository(private val stationDao: StationDao) {

    fun allStations(): List<Station> {
        return stationDao.allStations()
    }

    suspend fun addStation(station: Station) {
        stationDao.addStation(station)
    }

    suspend fun deleteStation(station: Station) {
        stationDao.deleteStation(station)
    }

}