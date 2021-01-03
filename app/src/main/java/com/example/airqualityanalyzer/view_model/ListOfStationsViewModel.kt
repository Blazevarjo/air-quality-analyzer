package com.example.airqualityanalyzer.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.airqualityanalyzer.model.AppDatabase
import com.example.airqualityanalyzer.model.Station
import com.example.airqualityanalyzer.model.repositories.StationRepository
import kotlinx.coroutines.launch

class ListOfStationsViewModel(application: Application) : AndroidViewModel(application) {

    val stations: LiveData<List<Station>>
    private val stationRepository: StationRepository

    init {
        val stationDao = AppDatabase.getDatabase(application).stationDao()
        stationRepository = StationRepository(stationDao)
        stations = stationDao.allStations()
    }

    fun addStation(station: Station) {
        viewModelScope.launch {
            stationRepository.addStation(station)
        }
    }

    fun deleteStation(station: Station) {
        viewModelScope.launch {
            stationRepository.deleteStation(station)
        }
    }

}