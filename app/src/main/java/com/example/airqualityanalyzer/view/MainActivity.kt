package com.example.airqualityanalyzer.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.work.*
import com.example.airqualityanalyzer.R
import com.example.airqualityanalyzer.model.FetchWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "fetchWork",
            ExistingPeriodicWorkPolicy.KEEP,
            fetchWorkRequest
        )
    }
}