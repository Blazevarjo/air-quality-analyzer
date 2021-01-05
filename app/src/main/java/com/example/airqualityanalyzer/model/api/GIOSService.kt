package com.example.airqualityanalyzer.model.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL="https://api.gios.gov.pl/pjp-api/rest/"

object GIOSService {
    private val retrofit by lazy{

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: GIOSRequest by lazy {
        retrofit
            .create(GIOSRequest::class.java)
    }
}