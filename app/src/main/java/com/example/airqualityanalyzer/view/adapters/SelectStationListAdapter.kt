package com.example.airqualityanalyzer.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.airqualityanalyzer.R
import com.example.airqualityanalyzer.model.entities.Station
import com.example.airqualityanalyzer.view_model.StationViewModel

class SelectStationListAdapter(var stations: LiveData<List<Station>>, var viewModel: StationViewModel) :
    RecyclerView.Adapter<SelectStationListAdapter.Holder>() {

    inner class Holder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.station_row_select, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var provinceName = holder.itemView.findViewById<TextView>(R.id.textProvinceName)
        var stationName = holder.itemView.findViewById<TextView>(R.id.textStationName)
        val check = holder.itemView.findViewById<ImageView>(R.id.imageViewCheck)

        provinceName.text = stations.value?.get(position)?.city?.commune?.provinceName ?: ""
        stationName.text = stations.value?.get(position)?.stationName ?: ""

        if(viewModel.isSelected(stations.value?.get(position)!!)) {
            check.visibility = View.VISIBLE
        }
        else {
            check.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            viewModel.toggleSelection(stations.value?.get(position)!!)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return stations.value?.size ?: 0
    }
}