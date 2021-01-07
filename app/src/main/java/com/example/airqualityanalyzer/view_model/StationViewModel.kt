package com.example.airqualityanalyzer.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.airqualityanalyzer.model.AppDatabase
import com.example.airqualityanalyzer.model.entities.Station
import com.example.airqualityanalyzer.model.repositories.GiosApiRepository
import com.example.airqualityanalyzer.model.repositories.StationRepository
import kotlinx.coroutines.launch

class StationViewModel(application: Application) : AndroidViewModel(application) {

    private val stationRepository: StationRepository =
        StationRepository(AppDatabase.getDatabase(application).stationDao())

    val stations: LiveData<List<Station>> = GiosApiRepository.getAllStations()

    val observedStations = stationRepository.allStations()

    val selected = mutableSetOf<Station>()

    lateinit var station: Station

    fun addStation(station: Station) {
        viewModelScope.launch {
            stationRepository.addStation(station)
        }
    }

    fun isSelected(station: Station): Boolean =
        selected.find{ it.id == station.id } != null

    fun toggleSelection(station: Station) {
        if(isSelected(station)) {
            selected.remove(station)
        }
        else {
            selected.add(station)
        }
    }

    fun deleteStation(station: Station) {
        viewModelScope.launch {
            stationRepository.deleteStation(station.id)
        }
    }

    fun deleteSelectedStations() {
        selected.forEach{ deleteStation(it) }
        selected.clear()
    }

}