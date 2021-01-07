package com.example.airqualityanalyzer.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.airqualityanalyzer.R
import com.example.airqualityanalyzer.databinding.FragmentListOfStationsBinding
import com.example.airqualityanalyzer.view_model.StationViewModel
import com.example.airqualityanalyzer.view.adapters.ListOfStationsListAdapter

class ListOfStationsFragment : Fragment() {
    private var _binding: FragmentListOfStationsBinding? = null
    private val binding get() = _binding!!


    private lateinit var myAdapter: ListOfStationsListAdapter
    private lateinit var myLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("ListofStation", "create")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = ViewModelProvider(requireActivity()).get(StationViewModel::class.java)

        myAdapter = ListOfStationsListAdapter(viewModel.observedStations, viewModel)
        myLayoutManager = LinearLayoutManager(context)

        viewModel.observedStations.observe(viewLifecycleOwner, {
            myAdapter.notifyDataSetChanged()
        })

        setHasOptionsMenu(true)

        _binding = FragmentListOfStationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuButtonDelete) {
            findNavController().navigate(R.id.action_listOfStationsFragment_to_deleteStationFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.listOfStations.apply {
            adapter = myAdapter
            layoutManager = myLayoutManager
        }

        binding.buttonAddStation.setOnClickListener {
            findNavController().navigate(R.id.action_listOfStationsFragment_to_addStationFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}