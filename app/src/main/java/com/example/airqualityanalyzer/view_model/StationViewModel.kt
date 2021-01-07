package com.example.airqualityanalyzer.view_model

import android.app.Application
import androidx.lifecycle.*
import com.example.airqualityanalyzer.model.AppDatabase
import com.example.airqualityanalyzer.model.entities.Station
import com.example.airqualityanalyzer.model.repositories.GiosApiRepository
import com.example.airqualityanalyzer.model.repositories.StationRepository
import kotlinx.coroutines.launch
import java.text.Collator
import java.util.*
import kotlin.Comparator

class StationViewModel(application: Application) : AndroidViewModel(application) {

    private val stationRepository: StationRepository =
        StationRepository(AppDatabase.getDatabase(application).stationDao())
    private val comparator: Comparator<Station>

    init {
        val polishCollator = Collator.getInstance(Locale("pl"))
        comparator = compareBy(polishCollator) { station: Station ->
            station.city?.commune?.provinceName ?: ""
        }
            .thenBy(polishCollator) { station: Station -> station.stationName }
    }

    val observedStations = Transformations.map(stationRepository.allStations()) {
        it.sortedWith(comparator)
    }

    val otherStations = getUnobservedStations()

    val selected = mutableSetOf<Station>()

    lateinit var station: Station

    fun addStation(station: Station) {
        viewModelScope.launch {
            stationRepository.addStation(station)
        }
    }

    fun isSelected(station: Station): Boolean =
        selected.find { it.id == station.id } != null

    fun toggleSelection(station: Station) {
        if (isSelected(station)) {
            selected.remove(station)
        } else {
            selected.add(station)
        }
    }

    fun deleteStation(station: Station) {
        viewModelScope.launch {
            stationRepository.deleteStation(station.id)
        }
    }

    fun deleteSelectedStations() {
        selected.forEach { deleteStation(it) }
        selected.clear()
    }

    private fun getUnobservedStations(): LiveData<List<Station>> {
        val result = MediatorLiveData<List<Station>>()
        var allStations: List<Station> = emptyList()
        var observedStations: List<Station> = emptyList()
        fun resultStation() {
            val stations = allStations.filterNot { station ->
                observedStations.contains(station)
            }
            result.value = stations.sortedWith(comparator)
        }

        result.addSource(GiosApiRepository.getAllStations())
        { items ->
            allStations = items
            resultStation()
        }
        result.addSource(stationRepository.allStations())
        { items ->
            observedStations = items
            resultStation()
        }
        return result
    }

}