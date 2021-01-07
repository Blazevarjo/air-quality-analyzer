package com.example.airqualityanalyzer.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.airqualityanalyzer.databinding.FragmentDeleteStationBinding
import com.example.airqualityanalyzer.view.adapters.SelectStationListAdapter
import com.example.airqualityanalyzer.view_model.StationViewModel

class DeleteStationFragment : Fragment() {

    private var _binding: FragmentDeleteStationBinding? = null
    private val binding get() = _binding!!

    private lateinit var myAdapter: SelectStationListAdapter
    private lateinit var myLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: StationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity()).get(StationViewModel::class.java)
        viewModel.selected.clear()

        myAdapter = SelectStationListAdapter(viewModel.observedStations, viewModel)
        myLayoutManager = LinearLayoutManager(context)

        viewModel.observedStations.observe(viewLifecycleOwner, {
            myAdapter.notifyDataSetChanged()
        })

        _binding = FragmentDeleteStationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.listOfStations.apply {
            adapter = myAdapter
            layoutManager = myLayoutManager
        }

        binding.buttonDeleteStation.setOnClickListener {
            viewModel.deleteSelectedStations()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}