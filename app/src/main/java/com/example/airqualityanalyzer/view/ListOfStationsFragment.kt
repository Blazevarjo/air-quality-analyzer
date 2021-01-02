package com.example.airqualityanalyzer.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.airqualityanalyzer.R
import com.example.airqualityanalyzer.view_model.ListOfStationsViewModel
import com.example.airqualityanalyzer.view_model.adapters.ListOfStationsListAdapter
import kotlinx.android.synthetic.main.fragment_list_of_stations.*

class ListOfStationsFragment : Fragment() {

    private lateinit var myAdapter: ListOfStationsListAdapter
    private lateinit var myLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var viewModel = ViewModelProvider(requireActivity()).get(ListOfStationsViewModel::class.java)

        myAdapter = ListOfStationsListAdapter(viewModel.stations)
        myLayoutManager = LinearLayoutManager(context)

        viewModel.stations.observe(viewLifecycleOwner, {
            myAdapter.notifyDataSetChanged()
        })

        return inflater.inflate(R.layout.fragment_list_of_stations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = listOfStations.apply {
            adapter = myAdapter
            layoutManager = myLayoutManager
        }

        buttonAddStation.setOnClickListener {
            //TODO
        }
    }

}