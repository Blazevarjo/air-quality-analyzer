package com.example.airqualityanalyzer.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.airqualityanalyzer.R
import com.example.airqualityanalyzer.model.entities.Station
import com.example.airqualityanalyzer.view_model.SensorDataViewModel
import com.example.airqualityanalyzer.view_model.StationViewModel

class ListOfStationsListAdapter(
    var stations: LiveData<List<Station>>,
    var viewModel: StationViewModel,
    var sensorDataViewModel: SensorDataViewModel
) :
    RecyclerView.Adapter<ListOfStationsListAdapter.Holder>() {

    inner class Holder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.station_row, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val provinceName = holder.itemView.findViewById<TextView>(R.id.textProvinceName)
        val stationName = holder.itemView.findViewById<TextView>(R.id.textStationName)

        provinceName.text = stations.value?.get(position)?.city?.commune?.provinceName ?: ""
        stationName.text = stations.value?.get(position)?.stationName ?: ""

        holder.itemView.setOnClickListener {
            val station = stations.value?.get(position)!!
            viewModel.station = station

            sensorDataViewModel.addAllSensorDataByStationId(station)

            holder.itemView.findNavController()
                .navigate(R.id.action_listOfStationsFragment_to_graphFragment)
        }
    }

    override fun getItemCount(): Int {
        return stations.value?.size ?: 0
    }
}