package com.example.airqualityanalyzer.view

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.airqualityanalyzer.R
import com.example.airqualityanalyzer.databinding.FragmentLineChartBinding
import com.example.airqualityanalyzer.utils.CustomMarker
import com.example.airqualityanalyzer.utils.XAxisDateFormatter
import com.example.airqualityanalyzer.utils.YAxisUnitFormatter
import com.example.airqualityanalyzer.view_model.GraphViewModel
import com.example.airqualityanalyzer.view_model.StationViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class LineChartFragment : Fragment() {
    private var _binding: FragmentLineChartBinding? = null
    private val binding get() = _binding!!

    private lateinit var graphViewModel: GraphViewModel
    private lateinit var stationViewModel: StationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        graphViewModel = ViewModelProvider(requireActivity()).get(GraphViewModel::class.java)
        stationViewModel = ViewModelProvider(requireActivity()).get(StationViewModel::class.java)

        setupChart()

        _binding = FragmentLineChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    private fun setupChart() {
        graphViewModel.sensorData.observe(viewLifecycleOwner) { sensorDataList ->
            val chart = binding.lineChart

            chart.fitScreen()

            val entries = arrayListOf<Entry>()
            for (data in sensorDataList) {
                entries.add(Entry(data.date.time.toFloat(), data.value.toFloat()))
            }

            val dataSet = LineDataSet(entries, "Values")
            dataSet.setDrawCircles(false)
            dataSet.fillColor = Color.LTGRAY
            dataSet.color = Color.GREEN
            dataSet.setDrawValues(false)
            dataSet.setDrawFilled(true)
            dataSet.isHighlightEnabled = true

            val lineData = LineData(dataSet)

            chart.setTouchEnabled(true)
            chart.data = lineData

            chart.description.isEnabled = false
            chart.legend.isEnabled = false
            chart.setExtraOffsets(0f, 0f, 40f, 0f)
            chart.marker = CustomMarker(activity, R.layout.marker_layout)

            chart.axisLeft.textColor = Color.WHITE
            chart.axisRight.isEnabled = false
            chart.xAxis.valueFormatter = XAxisDateFormatter()
            chart.xAxis.textColor = Color.WHITE
            chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            chart.xAxis.labelCount = 4
            chart.xAxis.isGranularityEnabled = true
            chart.xAxis.granularity = 1.0f
            chart.axisLeft.valueFormatter = YAxisUnitFormatter()


            chart.invalidate()
        }
    }
}