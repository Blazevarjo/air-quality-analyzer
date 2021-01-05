package com.example.airqualityanalyzer.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.airqualityanalyzer.model.AppDatabase
import com.example.airqualityanalyzer.model.entities.Station
import com.example.airqualityanalyzer.model.repositories.ApiRepository
import com.example.airqualityanalyzer.model.repositories.StationRepository
import kotlinx.coroutines.launch

class StationViewModel(application: Application) : AndroidViewModel(application) {

    private val stationRepository: StationRepository =
        StationRepository(AppDatabase.getDatabase(application).stationDao())

    val stations: LiveData<List<Station>> = ApiRepository.getAllStations()


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