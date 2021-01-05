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
import com.example.airqualityanalyzer.databinding.FragmentAddStationBinding
import com.example.airqualityanalyzer.view.adapters.ListOfStationsListAdapter
import com.example.airqualityanalyzer.view_model.StationViewModel

class AddStationFragment : Fragment() {
    private var _binding: FragmentAddStationBinding? = null
    private val binding get() = _binding!!

    private lateinit var myAdapter: ListOfStationsListAdapter
    private lateinit var myLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var viewModel = ViewModelProvider(requireActivity()).get(StationViewModel::class.java)

        //TODO
//        myAdapter = ListOfStationsListAdapter(viewModel.stations)
//        myLayoutManager = LinearLayoutManager(context)
//
//        viewModel.stations.observe(viewLifecycleOwner, {
//            myAdapter.notifyDataSetChanged()
//        })
        _binding = FragmentAddStationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.listOfStations.apply {
            adapter = myAdapter
            layoutManager = myLayoutManager
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}