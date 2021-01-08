package com.example.airqualityanalyzer.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.*
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        viewModel = ViewModelProvider(requireActivity()).get(GraphViewModel::class.java)
        viewModel.station = ViewModelProvider(requireActivity()).get(StationViewModel::class.java).station

        setObservers()

        _binding = FragmentGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chipGroupSensors.setOnCheckedChangeListener { group, checkedId ->
            viewModel.setSelectedSensorByParamCode(group.findViewById<Chip>(checkedId).text.toString())
            changeChipSelection()
        }

        binding.editTextDateBegin.inputType = InputType.TYPE_NULL
        binding.editTextDateEnd.inputType = InputType.TYPE_NULL

        binding.editTextDateBegin.setOnClickListener {
            showDateBeginDialog()
        }

        binding.editTextDateBegin.setOnFocusChangeListener{ v, hasFocus ->
            if(hasFocus) {
                showDateBeginDialog()
            }
        }

        binding.editTextDateEnd.setOnClickListener {
            showDateEndDialog()
        }

        binding.editTextDateEnd.setOnFocusChangeListener{ v, hasFocus ->
            if(hasFocus) {
                showDateEndDialog()
            }
        }
    }

    fun showDateBeginDialog() {
        var calendar = Calendar.getInstance()
        var dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            var timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                viewModel.dateBegin.value = calendar.time
            }

            TimePickerDialog(context,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true)
                .show()
        }

        DatePickerDialog(requireContext(),
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH))
            .show()
    }

    fun showDateEndDialog() {
        var calendar = Calendar.getInstance()
        var dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            var timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                viewModel.dateEnd.value = calendar.time
            }

            TimePickerDialog(context,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true)
                .show()
        }

        DatePickerDialog(requireContext(),
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH))
            .show()
    }

    fun initChips() {
        binding.chipSO2.isEnabled = false
        binding.chipC6H6.isEnabled = false
        binding.chipCO.isEnabled = false
        binding.chipNO2.isEnabled = false
        binding.chipO3.isEnabled = false
        binding.chipPM10.isEnabled = false
        binding.chipPM25.isEnabled = false

        var sensors = viewModel.stationSensors
        sensors.value?.forEach {
            when(it.param.paramCode) {
                "SO2" -> {binding.chipSO2.isEnabled = true}
                "C6H6" -> {binding.chipC6H6.isEnabled = true}
                "CO" -> {binding.chipCO.isEnabled = true}
                "NO2" -> {binding.chipNO2.isEnabled = true}
                "O3" -> {binding.chipO3.isEnabled = true}
                "PM10" -> {binding.chipPM10.isEnabled = true}
                "PM25" -> {binding.chipPM25.isEnabled = true}
            }
        }
    }

    fun changeChipSelection() {
        binding.chipSO2.isSelected = false
        binding.chipC6H6.isSelected = false
        binding.chipCO.isSelected = false
        binding.chipNO2.isSelected = false
        binding.chipO3.isSelected = false
        binding.chipPM10.isSelected = false
        binding.chipPM25.isSelected = false

        when(viewModel.selectedSensor.value?.param?.paramCode) {
            "SO2" -> {binding.chipSO2.isSelected = true}
            "C6H6" -> {binding.chipC6H6.isSelected = true}
            "CO" -> {binding.chipCO.isSelected = true}
            "NO2" -> {binding.chipNO2.isSelected = true}
            "O3" -> {binding.chipO3.isSelected = true}
            "PM10" -> {binding.chipPM10.isSelected = true}
            "PM25" -> {binding.chipPM25.isSelected = true}
        }
    }



    fun setObservers() {
        viewModel.stationSensors.observe(viewLifecycleOwner,{
            viewModel.setSelectedSensorToDefault()
            initChips()
        })

        viewModel.selectedSensor.observe(viewLifecycleOwner,{
            viewModel.updateSensorData()
            changeChipSelection()
        })

        viewModel.dateBegin.observe(viewLifecycleOwner,{
            viewModel.updateSensorData()
            var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
            binding.editTextDateBegin.setText(simpleDateFormat.format(viewModel.dateBegin.value!!).toString())
        })

        viewModel.dateEnd.observe(viewLifecycleOwner,{
            viewModel.updateSensorData()
            var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
            binding.editTextDateEnd.setText(simpleDateFormat.format(viewModel.dateEnd.value!!).toString())
        })

        viewModel.sensorData.observe(viewLifecycleOwner,{
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

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}