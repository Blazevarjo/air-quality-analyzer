package com.example.airqualityanalyzer.utils

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class XAxisDateFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
        return simpleDateFormat.format(Date(value.toLong()))
    }
}