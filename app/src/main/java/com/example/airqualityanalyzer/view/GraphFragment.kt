package com.example.airqualityanalyzer.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.airqualityanalyzer.databinding.FragmentGraphBinding
import com.example.airqualityanalyzer.view_model.GraphViewModel
import com.example.airqualityanalyzer.view_model.StationViewModel
import com.google.android.material.chip.Chip
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round


class GraphFragment : Fragment() {
    private var _binding: FragmentGraphBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GraphViewModel
    private lateinit var stationViewModel: StationViewModel

    private lateinit var chips: Sequence<Chip>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        viewModel = ViewModelProvider(requireActivity()).get(GraphViewModel::class.java)
        stationViewModel = ViewModelProvider(requireActivity()).get(StationViewModel::class.java)

        viewModel.initStationSensors(stationViewModel.station)

        setObservers()

        _binding = FragmentGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chips = binding.chipGroupSensors.children as Sequence<Chip>

        binding.chipGroupSensors.setOnCheckedChangeListener { group, checkedId ->
            viewModel.setSelectedSensorByParamCode(group.findViewById<Chip>(checkedId).text.toString())
        }

        binding.editTextDateBegin.inputType = InputType.TYPE_NULL
        binding.editTextDateEnd.inputType = InputType.TYPE_NULL

        binding.toolbar.title = stationViewModel.station.stationName

        binding.editTextDateBegin.setOnClickListener {
            showDateBeginDialog()
        }

        binding.editTextDateEnd.setOnClickListener {
            showDateEndDialog()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDateBeginDialog() {
        val calendar = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                viewModel.dateBegin.value = calendar.time
            }

            TimePickerDialog(
                context,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
                .show()
        }

        DatePickerDialog(
            requireContext(),
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
            .show()
    }

    private fun showDateEndDialog() {
        val calendar = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                viewModel.dateEnd.value = calendar.time
            }

            TimePickerDialog(
                context,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
                .show()
        }

        DatePickerDialog(
            requireContext(),
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
            .show()
    }

    private fun initChips() {
        //Disable all chips
        for (chip in chips) {
            chip.isEnabled = false
        }

        val sensors = viewModel.stationSensors

        //Enable chips when sensor with specific paramCode exist
        sensors.value?.forEach { sensor ->
            val chip = chips.find {
                Log.e("sensor param", sensor.param.paramCode)
                it.text == sensor.param.paramCode
            }
            chip?.isEnabled = true
        }

        // Check default sensor
        chips.find {
            it.text == viewModel.selectedSensor.value?.param?.paramCode
        }?.isChecked = true
    }

    private fun setObservers() {
        viewModel.stationSensors.observe(viewLifecycleOwner, {
            viewModel.setSelectedSensorToDefault()
            initChips()
        })

        viewModel.selectedSensor.observe(viewLifecycleOwner, {
            viewModel.updateSensorData()
        })

        viewModel.dateBegin.observe(viewLifecycleOwner, {
            viewModel.updateSensorData()
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
            binding.editTextDateBegin.setText(
                simpleDateFormat.format(viewModel.dateBegin.value!!).toString()
            )
        })

        viewModel.dateEnd.observe(viewLifecycleOwner, {
            viewModel.updateSensorData()
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
            binding.editTextDateEnd.setText(
                simpleDateFormat.format(viewModel.dateEnd.value!!).toString()
            )
        })

        viewModel.sensorData.observe(viewLifecycleOwner, {
            viewModel.updateProperties()
        })

        viewModel.max.observe(viewLifecycleOwner, {
            binding.textViewMax.text = viewModel.max.value?.round(2).toString()
        })

        viewModel.min.observe(viewLifecycleOwner, {
            binding.textViewMin.text = viewModel.min.value?.round(2).toString()
        })

        viewModel.std.observe(viewLifecycleOwner, {
            binding.textViewStd.text = viewModel.std.value?.round(2).toString()
        })

        viewModel.avg.observe(viewLifecycleOwner, {
            binding.textViewAvg.text = viewModel.avg.value?.round(2).toString()
        })
    }

    private fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }
}