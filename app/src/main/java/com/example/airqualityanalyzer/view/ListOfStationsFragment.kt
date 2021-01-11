package com.example.airqualityanalyzer.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.example.airqualityanalyzer.R
import com.example.airqualityanalyzer.databinding.FragmentListOfStationsBinding
import com.example.airqualityanalyzer.model.FetchWorker
import com.example.airqualityanalyzer.view_model.StationViewModel
import com.example.airqualityanalyzer.view.adapters.ListOfStationsListAdapter
import com.example.airqualityanalyzer.view_model.GraphViewModel
import com.example.airqualityanalyzer.view_model.SensorDataViewModel
import java.util.concurrent.TimeUnit

class ListOfStationsFragment : Fragment() {
    private var _binding: FragmentListOfStationsBinding? = null
    private val binding get() = _binding!!


    private lateinit var myAdapter: ListOfStationsListAdapter
    private lateinit var myLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        val stationViewModel =
            ViewModelProvider(requireActivity()).get(StationViewModel::class.java)
        val sensorDataViewModel =
            ViewModelProvider(requireActivity()).get(SensorDataViewModel::class.java)
        val graphViewModel =
            ViewModelProvider(requireActivity()).get(GraphViewModel::class.java)
        myAdapter = ListOfStationsListAdapter(
            stationViewModel.observedStations,
            stationViewModel,
            sensorDataViewModel,
            graphViewModel
        )
        myLayoutManager = LinearLayoutManager(context)

        stationViewModel.observedStations.observe(viewLifecycleOwner, {
            myAdapter.notifyDataSetChanged()
        })

        setHasOptionsMenu(true)

        _binding = FragmentListOfStationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_delete, menu)
        val item = menu.findItem(R.id.workerStatus)
        val workInfo = WorkManager.getInstance(requireContext())
            .getWorkInfosByTag("fetchWork").get()

        item.isChecked = !workInfo.isNullOrEmpty()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuButtonDelete -> findNavController().navigate(R.id.action_listOfStationsFragment_to_deleteStationFragment)
            R.id.workerStatus -> {
                val workInfo = WorkManager.getInstance(requireContext())
                    .getWorkInfosByTag("fetchWork").get()
                val isWorking: Boolean

                isWorking = if (workInfo.isNullOrEmpty()) {
                    false
                } else {
                    WorkManager.getInstance(requireContext())
                        .getWorkInfosByTag("fetchWork")
                        .get()[0].state in listOf(WorkInfo.State.ENQUEUED, WorkInfo.State.RUNNING)
                }

                if (isWorking) {
                    WorkManager.getInstance(requireContext()).cancelUniqueWork("fetchWork")
                    item.isChecked = false
                } else {
                    enqueueWork()
                    item.isChecked = true
                }

            }
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

    private fun enqueueWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val fetchWorkRequest: PeriodicWorkRequest =
            PeriodicWorkRequest.Builder(FetchWorker::class.java, 2, TimeUnit.DAYS)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    20,
                    TimeUnit.SECONDS
                )
                .setConstraints(constraints)
                .addTag("fetchWork")
                .build()

        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            "fetchWork",
            ExistingPeriodicWorkPolicy.KEEP,
            fetchWorkRequest
        )
    }

}