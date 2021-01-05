package com.example.airqualityanalyzer.model.daoInterfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.airqualityanalyzer.model.entities.Station

@Dao
interface StationDao {

    @Insert
    suspend fun addStation(station: Station)

    @Query("DELETE FROM station WHERE id = :stationId" )
    suspend fun deleteStation(stationId: Int)

    @Query("SELECT * FROM station")
    fun allStations(): LiveData<List<Station>>

}