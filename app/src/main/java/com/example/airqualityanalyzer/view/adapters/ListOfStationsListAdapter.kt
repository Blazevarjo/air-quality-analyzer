package com.example.airqualityanalyzer.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.airqualityanalyzer.R
import com.example.airqualityanalyzer.model.entities.Station

class ListOfStationsListAdapter(var stations: LiveData<List<Station>>) :
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
            //TODO
        }
    }

    override fun getItemCount(): Int {
        return stations.value?.size ?: 0
    }
}