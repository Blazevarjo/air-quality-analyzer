package com.example.airqualityanalyzer.utils

import android.content.Context
import android.widget.TextView
import com.example.airqualityanalyzer.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.*

class CustomMarker(context: Context?, layoutResource: Int) : MarkerView(context, layoutResource) {
    private val valueTextView = findViewById<TextView>(R.id.valueMarker)
    private val dateTextView = findViewById<TextView>(R.id.dateMarker)

    override fun getOffset(): MPPointF  = MPPointF(-(width / 2).toFloat(), -height.toFloat())


    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        valueTextView.text = "${e!!.y.round(2)} μg/m3"
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
        dateTextView.text = simpleDateFormat.format(Date(e.x.toLong()))
       super.refreshContent(e, highlight)
    }

    private fun Float.round(decimals: Int): Float {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return (kotlin.math.round(this * multiplier) / multiplier).toFloat()
    }
}